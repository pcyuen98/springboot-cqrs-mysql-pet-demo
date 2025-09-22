import { Component, Input, NgModule } from '@angular/core';
import { CommonService } from '../service/CommonService';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { PageBaseComponent } from './page-base.component';
import { GlobalConstants } from 'src/environments/GlobalConstants';

@Component({
    selector: 'app-util-success-be',
    template: `

  <ion-header>
    <ion-toolbar>
      <!-- Left aligned title -->
      <ion-title>
        <b>Success</b>
      </ion-title>

      <!-- Right aligned buttons -->
      <ion-buttons slot="end">
        <ion-icon name="close-circle" size="large" (click)="close()" class="ion-margin-start"></ion-icon>
      </ion-buttons>
    </ion-toolbar>
  </ion-header>

  <div class="success-box" style="color: green; border: 1px solid green; padding: 10px; border-radius: 5px;">
    <ul>
      <li><b>Message:</b> {{getSuccessMessage()}}</li>
    </ul>
  </div>

  `,
    styles: [`h1 { font-family: Lato; }`]
})
export class SuccessBEComponent {

    @Input() successMessage: string;
    @Input() pageBaseComponent!: PageBaseComponent;

    constructor(private commonService: CommonService) { }


    close() {
        if (this.pageBaseComponent) {
            this.pageBaseComponent.clearSuccessMessage()
        }
    }

    getSuccessMessage() {
        if (this.successMessage) {
            return this.successMessage
        }
        else {
            return GlobalConstants.globalBESuccess
        }

    }

}


@NgModule({
    declarations: [SuccessBEComponent],
    imports: [CommonModule, IonicModule],
    exports: [SuccessBEComponent]
})
export class SuccessBEModule { }
