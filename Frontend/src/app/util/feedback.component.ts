import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { AlertService } from 'src/app/service/AlertService';
import { UserService } from '../service/UserService';
import { Feedback } from '../models/feedback';
import { Learning } from '../models/learning';
import { User } from '../models/user';
import { GlobalConstants } from 'src/environments/GlobalConstants';
import { FormsModule } from '@angular/forms';
import { CommonHTTPService } from '../service/CommonHTTPService';
// @ts-ignore
@Component({
  selector: 'app-feedback',
  template: `
    <ion-spinner *ngIf="this.isLoading" size="large" name="crescent"></ion-spinner>
<ion-content *ngIf="!isLoading">

  <ion-list class="ion-no-padding">
    <ion-item lines="none">
      <ion-grid>
        <ion-row class="ion-align-items-center">
          <ion-col size="6">
            <label class="custom-label">Feedback Type</label>
          </ion-col>
          <ion-col size="6">
            <ion-select
              [(ngModel)]="feedbackOption"
              (ionChange)="onSelectChange($event)"
              interface="popover"
              cancelText="Cancel"
              okText="OK"
              placeholder="Select"
            >
              <ion-select-option value="User Experience">UX</ion-select-option>
              <ion-select-option value="GitHub Coding">GitHub Coding</ion-select-option>
              <ion-select-option value="Design">Design</ion-select-option>
              <ion-select-option value="Others">Others</ion-select-option>
            </ion-select>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-item>
  </ion-list>

  <ion-item>

  <ion-textarea
    autoGrow="true"
    [(ngModel)]="feedbackMsg"
    placeholder="Type your feedback here..."
    (keyup.enter)="onSearchInput($event)"
  ></ion-textarea>

    <ion-buttons slot="end">
      <ion-button color="primary" (click)="confirm()">Submit</ion-button>
      <ion-button *ngIf="feedbackOption || feedbackMsg" slot="end" fill="clear" size="small" color="medium"
          (click)="clearFeedback()">Clear</ion-button>
    </ion-buttons>
  </ion-item>

</ion-content>  `,
  styles: [`
  `]
})
export class FeedbackComponent {
  feedbackMsg: string | undefined;
  feedbackOption: any | undefined;
  isLoading: boolean = false
  constructor(
    private userService: UserService,
    private alertService: AlertService,
    private CommonHTTPService: CommonHTTPService
  ) { }

  clearFeedback() {
    this.feedbackMsg = undefined;
    this.feedbackOption = undefined;
  }

  onSelectChange(e: any) {
    this.feedbackOption = e.detail.value
  }
  onSearchInput(event: KeyboardEvent) {
    const input = this.feedbackMsg?.trim();

    if (event.key === 'Enter' && input) {
      this.updateFeedback().then(() => {});
    }
  }

  confirm() {
    const inputOption = this.feedbackOption?.trim();
    const inputMsg = this.feedbackMsg?.trim();

    if (inputOption || inputMsg) {
      this.updateFeedback().then(() => {} );
    } else {
      this.alertService.displayMsgBox("Please enter some feedback").then(() => {});
    }
  }

  async updateFeedback() {
    this.isLoading = true;

    const feedbackMsg = this.feedbackMsg?.trim() || "";
    const feedbackOption = this.feedbackOption?.trim() || "";

    // Validate input
    if (!feedbackMsg && !feedbackOption) {
      await this.alertService.displayMsgBox("Please enter some feedback");
      this.isLoading = false;
      return;
    }

    const feedback = new Feedback();
    feedback.feedbackType = 2;
    feedback.msg = `${feedbackOption}. ${feedbackMsg}`.trim();

    // Attach learning reference
    const learning = new Learning();
    learning.id = 2;
    feedback.learning = learning;

    await this.updateFeedbackSpringboot(feedback);
    this.isLoading = false;
  }


  async updateFeedbackSpringboot(feedback: Feedback) {

    let user: User = this.userService.getUserCookie()

    if (user != undefined) {
      feedback.userDTO = user
    }

    await this.CommonHTTPService.postResource(
      `${GlobalConstants.spring_boot_url}/feedback`, feedback
    );

    alert('Thank you for your feedback!');

  }
}

@NgModule({
  declarations: [FeedbackComponent],
  imports: [CommonModule, IonicModule, FormsModule],
  exports: [FeedbackComponent]
})
export class FeedbackModule { }
