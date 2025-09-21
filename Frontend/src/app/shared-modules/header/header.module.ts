import { NgModule } from '@angular/core';
import { HeaderComponent } from './header.component';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { HelloModule } from 'src/app/util/hello.component';
import { MenuModule } from 'src/app/util/menu.component';
@NgModule({
    imports: [
        CommonModule,
        IonicModule,
        FormsModule,
        HelloModule,
        MenuModule,
      ],
    declarations: [
        HeaderComponent,
      ],
      exports: [
        HeaderComponent,
      ]
})
export class HeaderModule {}
