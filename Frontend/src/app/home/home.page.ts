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
    headline: 'Miouw',
    
    src: 'assets/pic/spring-boot-keycloak-mysql.jpg'
  },
  {
    index: 1,
    headline: 'In The Wilderness',
    
    src: 'assets/pic/CQRSDiagram.png'
  },
  {
    index: 2,
    headline: 'For Your Current Mood',
    
    src: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/225363/guitar.jpg'
  },
  {
    index: 3,
    headline: 'Focus On The Writing',
    
    src: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/225363/typewriter.jpg'
  },
  {
    index: 4,
    headline: 'Focus On The Writing',
    
    src: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/225363/typewriter.jpg'
  }
]
}
