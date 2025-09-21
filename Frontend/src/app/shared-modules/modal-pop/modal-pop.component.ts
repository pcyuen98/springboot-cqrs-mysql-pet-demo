import { Component, Input, ViewChild } from '@angular/core';

import { ModalController } from '@ionic/angular';
import { CommonService } from '../../service/CommonService';
import { AskAIComponent } from 'src/app/util/ask-ai.component';
import { AlertService } from 'src/app/service/AlertService';


@Component({
  selector: 'app-modal-pop',
  templateUrl: 'modal-pop.component.html',
  styleUrls: ['modal-pop.css'],
  standalone: false,
})
export class ModalPopComponent {
  @ViewChild('askAIComponent') askAIComponent: AskAIComponent; // Access the DOM element

  @Input() title: string = '';
  @Input() message: string = '';
  @Input() data: any;
  @Input() isArray: boolean
  protected isQuickAsk: any;
  protected isLoading: boolean;

  constructor(
    private modalCtrl: ModalController,
    private commonService: CommonService,
    private alertService: AlertService,
  ) { }

  isDesktop() {
    return this.commonService.isDesktop()
  }
  async captureHighlight(): Promise<void> {
    const selection = window.getSelection();
    const selectedText = selection?.toString().trim() || '';

    if (!selectedText) return;

    this.isLoading = true;

    try {
      if (this.isQuickAsk) {
        const result = await this.alertService.displayMsgBox("Ask AI with the selected text?");
        if (result === 'ok') {
          await this.askAIComponent.submit(selectedText);
        }
      } else if (selectedText.length > 2) {
        this.copyIntoClipBoard(selectedText);
      }
    } finally {
      this.isLoading = false;
    }
  }


  copyIntoClipBoard(text: string) {
    const textarea = document.createElement('textarea');
    textarea.value = text;
    document.body.appendChild(textarea);

    // Select and copy the text
    textarea.select();
    document.execCommand('copy');

    // Clean up
    document.body.removeChild(textarea);

    alert("Copied to clipboard: " + text);
  }

  async asiAI() {
    await this.askAIComponent.submit(this.data)
  }

  flatten() {
    return this.commonService.flatten(this.data)
  }

  close() {
    this.modalCtrl.dismiss().then(() => {});
  }

}
