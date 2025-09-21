import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalPopComponent } from './modal-pop.component';
import { AskAIModule } from 'src/app/util/ask-ai.component';
import { IonicModule } from '@ionic/angular';

@NgModule({
    declarations: [ModalPopComponent],
    exports: [ModalPopComponent],
    imports: [
      CommonModule
      ,FormsModule
      ,AskAIModule
      ,IonicModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
  })
export class ModelPopModule { }
