import { Component, Injector } from '@angular/core';
import { PageBaseComponent } from '../util/page-base.component';
import 'swiper/element/bundle'; // 👈 enables Swiper Web Components
import { SlideData } from '../shared-modules/slider/slider.interface';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss']
})
export class HomePage extends PageBaseComponent {
  constructor(injector: Injector) {
    super(injector);
  }

   sliderData: SlideData[] = [
      {
    index: 0,
    headline: 'petstore',
    
    src: 'assets/pic/petstore.jpg'
  },
  {
    index: 1,
    headline: 'Sequence Diagram',
    
    src: 'assets/pic/spring-boot-keycloak-mysql.jpg'
  },
  {
    index: 2,
    headline: 'CQRS',
    
    src: 'assets/pic/CQRSDiagram.png'
  },

]
}
