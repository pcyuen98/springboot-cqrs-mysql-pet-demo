import { Directive, ElementRef, Renderer2, AfterContentInit, ContentChild, NgModule } from '@angular/core';
import { NgControl } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Directive({
  selector: '[highlightInvalid]' // usage: <ion-item highlightInvalid><ion-input formControlName="name"></ion-input></ion-item>
})
export class HighlightInvalidDirective implements AfterContentInit {
  @ContentChild(NgControl) ngControl?: NgControl;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngAfterContentInit(): void {
    if (!this.ngControl) return;
    const control = this.ngControl.control;
    if (!control) return;

    // React to Angular form validation changes
    control.statusChanges?.subscribe(() => this.updateHighlight());
    control.valueChanges?.subscribe(() => this.updateHighlight());

    // Focus in/out
    this.renderer.listen(this.el.nativeElement, 'focusin', () => {
      this.renderer.addClass(this.el.nativeElement, 'focused');
    });

    this.renderer.listen(this.el.nativeElement, 'focusout', () => {
      this.renderer.removeClass(this.el.nativeElement, 'focused');
      this.updateHighlight();
    });

    // Click event
    this.renderer.listen(this.el.nativeElement, 'click', () => {
      this.renderer.addClass(this.el.nativeElement, 'clicked');

      // Optional: remove after a short delay
      setTimeout(() => {
        this.renderer.removeClass(this.el.nativeElement, 'clicked');
      }, 200); // flashes the click effect
    });
  }

  private updateHighlight(): void {
    const control = this.ngControl?.control;
    if (!control) return;

    const invalid = control.invalid && (control.touched || control.dirty);

    if (invalid) {
      this.renderer.addClass(this.el.nativeElement, 'invalid');
    } else {
      this.renderer.removeClass(this.el.nativeElement, 'invalid');
    }
  }
}

@NgModule({
  declarations: [HighlightInvalidDirective],
  imports: [CommonModule],
  exports: [HighlightInvalidDirective]
})
export class HighlightInvalidModule {}
