import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HeaderModule } from '../shared-modules/header/header.module';
import { ModelPopModule } from '../shared-modules/modal-pop/modal-pop.module';
import { ModelPopArrayModule } from '../shared-modules/modal-pop-array/modal-pop-array.module';
import { FooterModule } from '../shared-modules/footer/footer.module';
import { WriterPage } from './writer.page';
import { WriterPageRoutingModule } from './writer-routing.module';
import { UrlValidatorHighlightModule } from '../util/url-validator-highlight.directive';
import { HighlightInvalidModule } from '../util/highlight-invalid.directive';

@NgModule({
  imports: [CommonModule, IonicModule, HeaderModule, WriterPageRoutingModule, ReactiveFormsModule,
    ModelPopModule, ModelPopArrayModule, ReactiveFormsModule, CommonModule
    , FooterModule, NgOptimizedImage, UrlValidatorHighlightModule, HighlightInvalidModule],
  declarations: [ WriterPage],
  exports: [WriterPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class WriterPageModule {}
