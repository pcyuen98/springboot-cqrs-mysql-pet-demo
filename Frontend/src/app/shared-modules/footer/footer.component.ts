import { Component, Injector } from '@angular/core';
import { PageBaseComponent } from 'src/app/util/page-base.component';

@Component({
  selector: 'app-footer',
  templateUrl: 'footer.component.html',
  styleUrls: ['footer.component.css'],
})
export class FooterComponent extends PageBaseComponent {

  constructor(injector: Injector,
) {
    super(injector);
  }
}
