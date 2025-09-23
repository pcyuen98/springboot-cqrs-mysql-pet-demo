import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ReactiveFormsModule } from '@angular/forms';

import { HeaderModule } from '../shared-modules/header/header.module';
import { ComponentWriterPageRoutingModule } from './component.writer-routing.module';
import { UrlValidatorHighlightModule } from '../util/url-validator-highlight.directive';
import { HighlightInvalidModule } from '../util/highlight-invalid.directive';
import { ComponentWriterPage } from './component.writer.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    NgOptimizedImage,
    HeaderModule,
    UrlValidatorHighlightModule,
    HighlightInvalidModule,
    ComponentWriterPageRoutingModule
  ],
  declarations: [ComponentWriterPage],
  exports: [ComponentWriterPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ComponentWriterPageModule {}
