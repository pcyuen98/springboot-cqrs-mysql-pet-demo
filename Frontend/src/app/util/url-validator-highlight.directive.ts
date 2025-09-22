import { CommonModule } from '@angular/common';
import { Directive, Input, ElementRef, Renderer2, OnInit, NgModule } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Directive({
  selector: '[appUrlValidatorHighlight]'
})
export class UrlValidatorHighlightDirective implements OnInit {
  @Input('appUrlValidatorHighlight') form!: FormGroup;
  @Input() controlName!: string;

  private errorTextEl?: HTMLElement = undefined;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngOnInit(): void {
    const control = this.form?.get(this.controlName);
    if (!control) return;

    control.statusChanges.subscribe(() => {
      this.toggleError(control.hasError('invalidUrl') && control.touched);
    });
  }

  private toggleError(show: boolean): void {
    const ionItem = this.el.nativeElement as HTMLElement;

    if (show) {
      // highlight ion-item
      this.renderer.setStyle(ionItem, 'border-left', '3px solid var(--ion-color-danger)');
      this.renderer.setStyle(ionItem, '--highlight-color-focused', 'var(--ion-color-danger)');

      // add error message if not exists
      if (!this.errorTextEl) {
        this.errorTextEl = this.renderer.createElement('ion-text');
        this.renderer.setAttribute(this.errorTextEl, 'color', 'danger');
        this.errorTextEl!.innerHTML = 
  'Invalid URL. Must start with http:// or https://';

        this.renderer.appendChild(
          ionItem.parentNode,
          this.errorTextEl
        );
      }
    } else {
      this.renderer.removeStyle(ionItem, 'border-left');
      this.renderer.removeStyle(ionItem, '--highlight-color-focused');

      if (this.errorTextEl) {
        this.renderer.removeChild(ionItem.parentNode, this.errorTextEl);
        this.errorTextEl = undefined;
      }
    }
  }
}

@NgModule({
  declarations: [UrlValidatorHighlightDirective],
  imports: [CommonModule],
  exports: [UrlValidatorHighlightDirective]
})
export class UrlValidatorHighlightModule {}
