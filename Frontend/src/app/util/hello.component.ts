import { Component, Input, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-util-hello',
  template: `
    Welcome <b><span class="module-text">{{ name }} </span></b>
    <!-- unused b><span class="module-text">{{ moduleName }} Module</span></b-->
  `,
  styles: [`
    .module-text {
      color:rgb(0, 144, 233);
    }
    h1 {
      font-family: Lato;
    }
  `]
})
export class HelloComponent {
  @Input() name: string;
  @Input() moduleName: string;

}

@NgModule({
  declarations: [HelloComponent],
  imports: [CommonModule, IonicModule],
  exports: [HelloComponent]
})
export class HelloModule { }
