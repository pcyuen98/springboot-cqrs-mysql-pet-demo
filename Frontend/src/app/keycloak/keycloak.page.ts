import { Component, Injector } from '@angular/core';
import { PageBaseComponent } from '../util/page-base.component';
import { Action } from '../models/action';
import { KeyCloakService } from '../service/KeyCloakService';
@Component({
  selector: 'app-keycloak',
  templateUrl: 'keycloak.page.html',
  styleUrls: ['keycloak.page.css']
})
export class KeycloakPage extends PageBaseComponent {

  constructor(injector: Injector,
    private keyCloakService: KeyCloakService
) {
    super(injector);
  }

  tokenActions: Action[] = [
    { label: 'Secure Headers', handler: () => this.commonHTTPService.getHeaderDetails() },
    { label: 'Test Token', handler: () => this.keyCloakService.testBEAuthorization() },
    { label: 'Token Details', handler: () => this.viewTokenDetails() },
    { label: '🕸 Keycloak Role Test', handler: () => this.keyCloakService.isKeycloakRole() },
    { label: '🕸 Reader Role Test', handler: () => this.keyCloakService.isReaderRole() },
  ];

  cookiesActions: Action[] = [
    { label: 'User Cookies', handler: () => this.getUserCookies() },
    { label: 'All Cookies', handler: () => this.getAllCookies() },
  ];

  frontEndActions: Action[] = [
    {
      label: '🕸 Simulate Error',
      color: 'danger',
      isButtonErrorType: true,
      isClearButtonErrorType: false,
      handler: () => this.testGeneralError()
    },
    {
      label: 'Clear Error Message',
      color: 'medium',
      isButtonErrorType: true,
      isClearButtonErrorType: true,
      handler: () => this.clearErrorMessage()
    },
  ];

  springbootActions: Action[] = [
    { label: '🕸 Security Context', handler: () => this.keyCloakService.getSecurityContext() },
    { label: '🕸 Application Context', handler: () => this.keyCloakService.getFilteredBeanNames() },
  ];

  viewTokenDetails(): void {
    const tokenData = this.userService.getTokenData();
    this.commonService.openPopModal(
      'Keycloak Login Token Details',
      'This is to check data return from keycloak server',
      tokenData
    ).then(() => {});
  }

  getUserCookies(): void {
    const userCookie = this.userService.getUserCookie();
    this.commonService.openPopModal(
      'User Cookies Details',
      'Cookies is stored upon login',
      userCookie
    ).then(() => {});
  }

  getAllCookies(): void {
    const cookies = this.userService.getAllCookies();
    this.commonService.openPopArrayModal(
      'All Stored Cookies Details',
      'This is to verify cookies being stored upon login',
      cookies
    ).then(() => {});
  }

  async testGeneralError(): Promise<void> {
    const data = await this.keyCloakService.testGeneralError();
    if (data?.error) {
      this.errorService.handleError("Error Simulation", "Frontend and Backend", data.error)
    }
  }


}
