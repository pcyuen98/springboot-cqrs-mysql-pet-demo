import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FooterComponent } from './footer.component';
import { ErrorBEModule } from 'src/app/util/error-be.component';
@NgModule({
    imports: [
        CommonModule,
        IonicModule,
        ErrorBEModule
      ],
    declarations: [
        FooterComponent,
      ],
      exports: [
        FooterComponent,
      ]
})
export class FooterModule {}
