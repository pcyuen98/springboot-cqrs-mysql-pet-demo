import { Component, Input, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuestionAIPayload } from '../models/question-ai-model';
import { CommonHTTPService } from '../service/CommonHTTPService';
import { CommonService } from '../service/CommonService';
import { IonicModule } from '@ionic/angular';

@Component({
    selector: 'app-util-ask-ai',
    template: `
  <span *ngIf="this.isLoading" >
   <b><small> Checking with AI. Wait a moment </small></b>
  <ion-spinner size="medium" name="crescent"></ion-spinner>
  </span>
  `,
    styles: [`
  `]
})
export class AskAIComponent {
    @Input() ques: string;
    @Input() askAI: boolean;

    constructor(
        private commonHTTPService: CommonHTTPService,
        private commonService: CommonService,
    ) {
    }
    isLoading = false

    async submit(ques: string): Promise<void>  {
        this.isLoading = true
        this.ques = "Explain what is this in 200 words and bullet points in answer element with html tag and color text for angular UX display. " + JSON.stringify(ques)
        const payload: QuestionAIPayload = {
            question: this.ques,
            answer: '',
            isValidApplicationSuggestion: null,
            isHarmful: null,
            harmfulType: ''
        };

        return this.askAIGeneralQues(payload)
    }


  trim(value: string, maxLength: number = 500): string {
    if (!value) return '';
    return value.length > maxLength ? value.slice(0, maxLength) + '...' : value;
  }

    async askAIGeneralQues(questionAIPayload: QuestionAIPayload): Promise<void> {
        let ques = "fill in the blank, do not change the json elements and return in this json format only for python processing " + JSON.stringify(questionAIPayload)
        ques = this.trim(ques)
        const data = await this.commonHTTPService.askAIGeneralQues(ques);
        this.commonService.openSlideAIModal(data).then(() => { });
        this.isLoading = false
        //alert(JSON.stringify(data))
    }
}

@NgModule({
    declarations: [AskAIComponent],
    imports: [CommonModule, IonicModule],
    exports: [AskAIComponent]
})
export class AskAIModule { }
