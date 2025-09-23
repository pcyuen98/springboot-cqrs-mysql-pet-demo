import { NgModule } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HeaderModule } from '../shared-modules/header/header.module';
import { QueryPageRoutingModule } from './query-routing.module';
import { FooterModule } from '../shared-modules/footer/footer.module';
import { IsAdminModule } from '../util/role.directive';
import { QueryPage } from './query.page';
import { ComponentWriterPageModule } from '../writer/component.writer.module';

@NgModule({
  imports: [CommonModule, IonicModule, HeaderModule, QueryPageRoutingModule, ReactiveFormsModule,
    ReactiveFormsModule, CommonModule
    , FooterModule, NgOptimizedImage, ComponentWriterPageModule, IsAdminModule],
  declarations: [QueryPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ReaderPageModule { }
