import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { Cookie } from 'ng2-cookies';
import { CommonService } from './CommonService';
import { UserService } from './UserService';
import { ErrorService } from './ErrorService';

@Injectable({
  providedIn: 'root'
})
export class CommonHTTPService {

  private _headers: HttpHeaders | null = null;

  constructor(
    private http: HttpClient,
    private commonService: CommonService,
    private userService: UserService,
    private errorService: ErrorService
  ) { }

  /**
   * Lazy-loaded singleton HTTP headers
   */
  get headers(): HttpHeaders {
    if (!this._headers) {
      this._headers = this.buildHeaders();
    }
    return this._headers;
  }

  /**
   * Builds new HttpHeaders instance
   */
  public buildHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${Cookie.get('access_token') || ''}`
    });
  }

  /**
   * Displays a modal with the result of an HTTP GET request
   */
  public async handleRequest(title: string, message: string, url: string): Promise<void> {
    try {
      const data = await this.getResource(url);
      await this.commonService.openPopModal(title + " Successful", message, data);
    } catch (error) {
      this.handleHttpError(error, 'POST', url);
      throw error;
    }
  }

  /**
 * Displays a modal with the result of an HTTP POST request
 */
  public async handlePostRequest(title: string, message: string, url: string, body: any): Promise<void> {
    try {
      const data = await this.postResource(url, body)
      await this.commonService.openPopModal(title + " Successful", message, data);
    } catch (error) {
      await this.commonService.openPopModal(`Error ${title}`, 'Error Details', error);
    }
  }

  async askAIGeneralQues(ques: any): Promise<any> {
    const headers = { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' };
    const resourceUrl = GlobalConstants.miniURL + '/api/chat'
    try {
      return await firstValueFrom(this.http.post<any>(GlobalConstants.miniURL + '/api/chat', ques, { headers }));
    } catch (error) {
      this.handleHttpError(error, 'POST', resourceUrl);
      throw error;
    }
  }
  /**
   * Shows current HTTP headers in a popup
   */
  getHeaderDetails(): void {
    this.commonService.openPopModal(
      'Restful HTTP Header',
      'Header Details for HTTP Restful services',
      this.getHeadersAsObject()
    ).then(() => {});
  }

  /**
   * Converts HttpHeaders to plain object for inspection
   */
  private getHeadersAsObject(): { [key: string]: string | string[] } {
    const result: { [key: string]: string | string[] } = {};
    this.headers.keys().forEach(key => {
      result[key] = this.headers.getAll(key) || '';
    });
    return result;
  }

  /**
   * Executes GET request with authorization header
   */
  async getResource(resourceUrl: string): Promise<any> {
    if (this.userService.isTokenExpired()) {
      throw new Error('Token is expired.');
    }

    try {
      return await firstValueFrom(this.http.get<any>(resourceUrl, { headers: this.headers }));
    } catch (error) {
      this.handleHttpError(error, 'GET', resourceUrl);
      throw error;
    }
  }

  /**
   * Executes POST request with authorization header
   */
  async postResource(resourceUrl: string, body: any): Promise<any> {
    try {
      return await firstValueFrom(this.http.post<any>(resourceUrl, body, { headers: this.headers }));
    } catch (error) {
      this.handleHttpError(error, 'POST', resourceUrl);
    }
  }

      /**
   * Executes POST request with authorization header
   */
  async putResource(resourceUrl: string, body: any): Promise<any> {
    try {
      return await firstValueFrom(this.http.put<any>(resourceUrl, body, { headers: this.headers }));
    } catch (error) {
      this.handleHttpError(error, 'POST', resourceUrl);
    }
  }

  /**
   * Logs errors for debugging purposes
   */
  private handleHttpError(error: any, method: string, url: string): void {
    this.errorService.handleError("HTTP Error", "Backend", error)
    if (error instanceof HttpErrorResponse) {
      console.error(`HTTP ${method} Error on ${url}:`, error.status, error.message);
    } else {
      console.error(`Unexpected Error during ${method} request to ${url}:`, error);
    }
  }
}
