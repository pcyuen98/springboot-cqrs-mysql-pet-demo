import { NgModule } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HeaderModule } from '../shared-modules/header/header.module';
import { ReaderPage } from './reader.page';
import { ReaderPageRoutingModule } from './reader-routing.module';
import { FooterModule } from '../shared-modules/footer/footer.module';
import { WriterPageModule } from '../writer/writer.module';
import { IsAdminModule } from '../util/role.directive';

@NgModule({
  imports: [CommonModule, IonicModule, HeaderModule, ReaderPageRoutingModule, ReactiveFormsModule,
    ReactiveFormsModule, CommonModule
    , FooterModule, NgOptimizedImage, WriterPageModule, IsAdminModule],
  declarations: [ReaderPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ReaderPageModule { }
