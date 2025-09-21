import { Component } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../service/UserService';
import { AuthService } from '../../service/AuthService';

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css'],
})
export class HeaderComponent {
  constructor(
    private userService: UserService,
    private authService: AuthService,
  ) { }

  login(): void {
    this.authService.login();
  }

  logout(): void {

    this.authService.logout();
    this.userService.setUserCookie(new User());
  }

  isLogin(): boolean {
    return this.userService.isLogin();
  }

  getUserCookies(): User {
    return this.userService.getUserCookie();
  }


}
