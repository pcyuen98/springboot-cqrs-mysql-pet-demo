import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { HomePage } from './home.page';

import { HomePageRoutingModule } from './home-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderModule } from '../shared-modules/header/header.module';
import { FooterModule } from '../shared-modules/footer/footer.module';


@NgModule({
  imports: [CommonModule, IonicModule, HeaderModule, HomePageRoutingModule, ReactiveFormsModule,
    FooterModule, NgOptimizedImage
  ],
  declarations: [ HomePage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomePageModule {}
