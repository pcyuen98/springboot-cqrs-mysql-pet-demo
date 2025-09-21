import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ButtonHandlerComponent } from './button-handler.component';
@NgModule({
    imports: [
        CommonModule,
        IonicModule,
      ],
    declarations: [
        ButtonHandlerComponent,
      ],
      exports: [
        ButtonHandlerComponent,
      ]
})
export class ButtonHandlerModule {}
