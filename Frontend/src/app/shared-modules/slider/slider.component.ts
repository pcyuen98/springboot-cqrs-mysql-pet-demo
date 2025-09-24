import { Component, OnInit, Input } from '@angular/core';
import { SlideData } from './slider.interface';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss']
})

export class SliderComponent implements OnInit {
  @Input() sliderData: SlideData[];

  currentSlide = 0;

  constructor() { }

  ngOnInit() {
  }

  onSlideClick(index: number) {
    if (this.currentSlide !== index) {
      this.currentSlide = index;
    }
  }

    openImage(url: string) {
    window.open(url, '_blank');  // opens in new tab
  }

  onPreviousClick() {
    const previous = this.currentSlide - 1
    this.currentSlide = previous < 0 ? this.sliderData.length - 1 : previous;
    console.log('previous clicked, new current slide is: ', this.currentSlide);
  }

  onNextClick() {
    const next = this.currentSlide + 1
    this.currentSlide = next === this.sliderData.length ? 0 : next;
    console.log('next clicked, new current slide is: ', this.currentSlide);
  }

}
