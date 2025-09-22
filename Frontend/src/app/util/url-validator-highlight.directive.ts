import { CommonModule } from '@angular/common';
import { Component, Input, NgModule } from '@angular/core';
import { FormControl, ReactiveFormsModule, AbstractControl, ValidationErrors } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-url-validator-highlight',
  template: `
    <ion-item [class.invalid]="showError">
      <ion-label position="floating">
        <span class="required">*</span> Photo URL
      </ion-label>
      <ion-input type="text" [formControl]="control"></ion-input>
    </ion-item>

    <ion-text *ngIf="showError" color="danger" class="ion-padding-start">
      {{ errorMessage }}
    </ion-text>
  `,
  styles: [`
    ion-item.invalid {
      border-left: 3px solid var(--ion-color-danger);
      --highlight-color-focused: var(--ion-color-danger);
    }

    .required {
      color: var(--ion-color-danger); /* Ionic red */
      font-weight: bold;
      margin-right: 2px;
    }
  `]
})
export class UrlValidatorHighlightComponent {
  @Input() control!: FormControl;
  @Input() errorMessage: string = 'Invalid URL. Must start with http:// or https://';

  get showError(): boolean {
    return !!(
      this.control &&
      this.control.hasError('invalidUrl') &&
      (this.control.dirty || this.control.touched)
    );
  }
}

/**
 * Custom URL validator for FormControl
 */
export function urlValidator(control: AbstractControl): ValidationErrors | null {
  if (!control.value) return null; // empty is handled by required
  const pattern = /^https?:\/\//i; // only check if it starts with http/https
  return pattern.test(control.value) ? null : { invalidUrl: true };
}

@NgModule({
  declarations: [UrlValidatorHighlightComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule
  ],
  exports: [UrlValidatorHighlightComponent]
})
export class UrlValidatorHighlightModule {}
