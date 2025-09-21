import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { Cookie } from 'ng2-cookies';
import { UserService } from './UserService';
import { CommonService } from './CommonService';
import { CommonHTTPService } from './CommonHTTPService';
import { AlertService } from './AlertService';
import { Role } from '../models/role';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorService } from './ErrorService';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private tokenUrl = `${GlobalConstants.keycloakURL}/realms/${GlobalConstants.realm}/protocol/openid-connect/token`;
  private authUrl = `${GlobalConstants.keycloakURL}/realms/${GlobalConstants.realm}/protocol/openid-connect/auth`;
  private logoutUrl = `${GlobalConstants.keycloakURL}/realms/${GlobalConstants.realm}/protocol/openid-connect/logout`;

  public clientId = GlobalConstants.clientID;
  public redirectUri = GlobalConstants.redirect;

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private commonService: CommonService,
    private commonHTTPService: CommonHTTPService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private errorService: ErrorService
  ) { }

  login(): void {
    window.location.href = `${this.authUrl}?response_type=code&scope=openid%20profile&client_id=${this.clientId}&redirect_uri=${this.redirectUri}`;
  }

  retrieveCodeFromRedirectedKeyCloak(code: string): void {
    const params = new URLSearchParams({
      grant_type: 'authorization_code',
      client_id: this.clientId,
      redirect_uri: this.redirectUri,
      code
    });

    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' });

    this.http.post(this.tokenUrl, params.toString(), { headers }).subscribe({
      next: async (token: any) => {
        const user: User = await this.saveToken(token);
        let result = await this.loginBE(user)
        await this.redirectToMainPage(result)

      },
      error: (error: any) => {
        this.commonService.openPopModal('Error verifying token. Logging out!', 'Error Details', error).then(() => {
          // Handle logic after the modal is closed/acknowledged
          console.log('Modal closed');
          this.logout();

        });
        this.logout();
      }
    });
  }

  private async redirectToMainPage(result: any) {
    if (result.error) {
      const result = await this.alertService.displayMsgBox("Error logging to BE. Logging out")

      if (result === 'ok') {
        this.logout();
      }
    }
    else {
      const isFromMenu: boolean = this.route.snapshot.paramMap.get('redirect') === 'menu';

      // only redirect if from login
      // this to shortcut redirection based on different login
      if (!isFromMenu) {
        if (this.userService.hasRole(Role.Reader)) {
          await this.router.navigate(['/reader']);
        } else if (this.userService.hasRole(Role.Keycloak)) {
          await this.router.navigate(['/keycloak']);
        } else {
          await this.router.navigate(['/home']);
        }
      }
    }
  }

  private async saveToken(token: any): Promise<any> {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    Cookie.set("id_token", token.id_token, expireDate);

    const payload = this.decodeToken(token.access_token);
    const user = this.createUserFromPayload(payload);
    if (user.username) {
      this.userService.setUserCookie(user);
    }

    return user
  }

  async loginBE(user: User): Promise<any> {
    let userBE
    try {
      userBE = await this.commonHTTPService.postResource(
        `${GlobalConstants.spring_boot_url}/login`, user)
    } catch (error: any) {
      this.errorService.handleError("Error Logging to Backend", "Backend", error)
      return error
    }
    return userBE
  }

  private createUserFromPayload(payload: any): User {
    const user = new User();
    user.username = payload.preferred_username;
    user.name = payload.given_name;
    user.surname = payload.family_name;
    user.token = payload;

    user.expiry = new Date(payload.exp * 1000).toLocaleString('en-US', {
      timeZone: 'Asia/Kuala_Lumpur'
    });

    return user;
  }

  private decodeToken(token: string): any {
    try {
      const payload = token.split('.')[1];
      return JSON.parse(window.atob(payload));
    } catch (e) {
      console.error("Failed to decode token:", e);
      return {};
    }
  }

  logout(): void {
    const token = Cookie.get('id_token');
    ['access_token', 'id_token', 'user'].forEach(key => {
      Cookie.delete(key);
      Cookie.delete(key, '/');
    });

    window.location.href = `${this.logoutUrl}?id_token_hint=${token}&post_logout_redirect_uri=${this.redirectUri}`;
  }

}
