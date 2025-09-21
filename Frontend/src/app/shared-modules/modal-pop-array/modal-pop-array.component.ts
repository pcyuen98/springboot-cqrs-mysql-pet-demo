import { Component } from '@angular/core';
import { ModalPopComponent } from '../modal-pop/modal-pop.component';

@Component({
  selector: 'app-modal-pop-array',
  templateUrl: 'modal-pop-array.component.html',
  styleUrls: ['../modal-pop/modal-pop.css'],
})
export class ModalPopArrayComponent extends ModalPopComponent {
  expandedIndex: number | null = null;

  toggle(index: number): void {
    this.expandedIndex = this.expandedIndex === index ? null : index;
  }

  tryParseJson(value: string) {
    try {
      return JSON.parse(value);
    } catch {
      return null;
    }
  }

}
