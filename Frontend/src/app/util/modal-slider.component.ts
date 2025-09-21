import { Component, Input, NgModule } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { ModalController } from '@ionic/angular';
import { QuestionAIPayload } from 'src/app/models/question-ai-model';
import { FeedbackModule } from "./feedback.component";
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-modal-slider',
    template: `
<ion-header>
  <ion-toolbar>
    <b>&nbsp; &nbsp; {{title}}</b>
    <ion-icon slot="end" name="close-circle"  size="large" (click)="cancel()" ></ion-icon>
  </ion-toolbar>
</ion-header>
<ion-content>
<span *ngIf="data && data.answer">
    <div [innerHTML]="data.answer"></div>
</span>
  <app-feedback></app-feedback>
</ion-content>

  `
})

export class ModalSliderComponent {
    @Input() title: string;
    @Input() data: QuestionAIPayload;

    constructor(
        private modalCtrl: ModalController,
    ) { }

    cancel() {
        return this.modalCtrl.dismiss(null, 'cancel');
    }
}
@NgModule({
    declarations: [ModalSliderComponent],
    exports: [ModalSliderComponent],
    imports: [
        CommonModule,
        IonicModule,
        FeedbackModule
    ],
})
export class ModelSliderModule { }
