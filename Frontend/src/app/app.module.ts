import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { IonicStorageModule } from '@ionic/storage-angular';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import "@angular/compiler";
import { DatePipe } from '@angular/common';
import { KeycloakAngularModule } from 'keycloak-angular';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ErrorInterceptor } from './service/ErrorInterceptorService';
import { AuthGuard } from './service/AuthGuard';


@NgModule({
  declarations: [AppComponent],

  imports: [KeycloakAngularModule, BrowserModule, ReactiveFormsModule, IonicModule.forRoot(),
    AppRoutingModule, IonicStorageModule.forRoot(), BrowserAnimationsModule],
  providers: [
    {
      provide: RouteReuseStrategy, useClass: IonicRouteStrategy
    },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    provideHttpClient(withFetch()),
    DatePipe,
    AuthGuard
  ],
  bootstrap: [AppComponent],
  exports: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

})
export class AppModule { }
