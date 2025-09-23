import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';

import { HeaderModule } from '../shared-modules/header/header.module';
import { WriterPage } from './writer.page';
import { WriterPageRoutingModule } from './writer-routing.module';
import { UrlValidatorHighlightModule } from '../util/url-validator-highlight.directive';
import { HighlightInvalidModule } from '../util/highlight-invalid.directive';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    NgOptimizedImage,
    HeaderModule,
    UrlValidatorHighlightModule,
    HighlightInvalidModule,
    WriterPageRoutingModule
  ],
  declarations: [WriterPage],
  exports: [WriterPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class WriterPageModule {}
