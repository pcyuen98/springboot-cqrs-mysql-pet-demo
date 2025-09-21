import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HeaderModule } from '../shared-modules/header/header.module';
import { ModelPopModule } from '../shared-modules/modal-pop/modal-pop.module';
import { ModelPopArrayModule } from '../shared-modules/modal-pop-array/modal-pop-array.module';
import {  ErrorBEModule } from '../util/error-be.component';
import { KeycloakPage } from './keycloak.page';
import { KeycloakPageRoutingModule } from './keycloak-routing.module';
import { ModelSliderModule } from '../util/modal-slider.component';
import { ButtonHandlerModule } from '../shared-modules/button-handler/button-handler.module';
import { FooterModule } from '../shared-modules/footer/footer.module';

@NgModule({
  imports: [ CommonModule, IonicModule, HeaderModule, KeycloakPageRoutingModule, ReactiveFormsModule, 
    ModelSliderModule,ModelPopModule, ModelPopArrayModule
    , ErrorBEModule
    , ButtonHandlerModule
    , FooterModule
  ],
  declarations: [KeycloakPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class KeycloakPageModule {}
