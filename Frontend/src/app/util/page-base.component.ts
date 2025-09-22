import { Component, Injector, NgModule, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import {
    AlertController,
} from '@ionic/angular';
import {
    HttpClient
} from '@angular/common/http';
import {
    DomSanitizer
} from '@angular/platform-browser';

import { CommonService } from '../service/CommonService';
import { UserService } from '../service/UserService';
import { CommonHTTPService } from '../service/CommonHTTPService';
import { ActivatedRoute, Router } from '@angular/router';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { ErrorBEComponent, ErrorBEModule } from './error-be.component';
import { ErrorService } from '../service/ErrorService';
import { AlertService } from '../service/AlertService';
import { AuthService } from '../service/AuthService';

@Component({
    selector: 'app-base',
    template: `
  `
})
export class PageBaseComponent {
    @ViewChild('errorBEComponent') errorBEComponent: ErrorBEComponent; // Access the DOM element

    protected sanitizer: DomSanitizer;
    protected alertController: AlertController;
    protected http: HttpClient;
    protected commonService: CommonService;
    protected userService: UserService;
    protected commonHTTPService: CommonHTTPService;
    protected router: Router;
    protected route: ActivatedRoute
    protected errorService: ErrorService
    protected alertService: AlertService
    protected authService: AuthService

    constructor(protected injector: Injector) {
        this.sanitizer = this.injector.get(DomSanitizer);
        this.alertController = this.injector.get(AlertController);
        this.http = this.injector.get(HttpClient);
        this.commonService = this.injector.get(CommonService);
        this.userService = this.injector.get(UserService);
        this.commonHTTPService = this.injector.get(CommonHTTPService);
        this.router = this.injector.get(Router);
        this.route = this.injector.get(ActivatedRoute);
        this.errorService = this.injector.get(ErrorService);
        this.alertService = this.injector.get(AlertService);
        this.authService = this.injector.get(AuthService)
    }

    public clearErrorMessage(): void {
        GlobalConstants.globalBEError = undefined
    }

    public clearSuccessMessage(): void {
        GlobalConstants.globalBESuccess = undefined
    }

    // --- UI Actions ---
    protected async openSlideModal(): Promise<void> {
        if (!this.userService.isLogin()) {
            const result = await this.alertService.displayMsgBox("Login not found. Enter username as keycloak, password as password. Login now?");
            if (result === 'ok') {
                this.authService.login();
            }
            return;
        }

        this.commonService.openSlideModal().then(() => { });
    }

    getGlobalError(): any | undefined {
        return GlobalConstants.globalBEError || undefined;
    }

    getGlobalSuccess(): any | undefined {
        return GlobalConstants.globalBESuccess || undefined;
    }
}

@NgModule({
    declarations: [PageBaseComponent],
    imports: [CommonModule, IonicModule, ErrorBEModule
        ,
    ],
    exports: [PageBaseComponent]
})
export class PageBaseModule {
}
