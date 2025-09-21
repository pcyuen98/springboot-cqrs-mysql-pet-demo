import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { CommonHTTPService } from './CommonHTTPService';

@Injectable({
  providedIn: 'root'
})
export class KeyCloakService {

  private _headers: HttpHeaders | null = null;

  constructor(
    private httpCommonService: CommonHTTPService,
  ) { }

  /**
   * Lazy-loaded singleton HTTP headers
   */
  get headers(): HttpHeaders {
    if (!this._headers) {
      this._headers = this.httpCommonService.buildHeaders();
    }
    return this._headers;
  }

  // === Public API Methods ===

  async testBEAuthorization(): Promise<void> {
    await this.httpCommonService.handleRequest(
      'Restful Testing BE Authorization',
      'Restful Details',
      `${GlobalConstants.spring_boot_test_url}/test`
    );
  }

  async getFilteredBeanNames(): Promise<void> {
    await this.httpCommonService.handleRequest(
      'Restful SpringBoot Application Context Bean',
      'Restful Details',
      `${GlobalConstants.spring_boot_test_url}/context/info`
    );
  }

  async getSecurityContext(): Promise<void> {
    await this.httpCommonService.handleRequest(
      'Restful SpringBoot KeycloakSecurityContext',
      'Restful Details',
      `${GlobalConstants.spring_boot_test_url}/security/context`
    );
  }

  async isKeycloakRole(): Promise<void> {
    await this.httpCommonService.handleRequest(
      'Restful Keycloak Role Test',
      'Restful Details',
      `${GlobalConstants.spring_boot_test_url}/keycloak/role`
    );
  }

  async isReaderRole(): Promise<void> {
    await this.httpCommonService.handleRequest(
      'Restful Reader Role Test',
      'Restful Details',
      `${GlobalConstants.spring_boot_test_url}/reader/role`
    );
  }

  async testGeneralError(): Promise<any> {
    try {
      return await this.httpCommonService.getResource(`${GlobalConstants.spring_boot_test_url}/error/test`);
    } catch (error) {
      console.error('Error in testGeneralError:', error);
      return error;
    }
  }

}
