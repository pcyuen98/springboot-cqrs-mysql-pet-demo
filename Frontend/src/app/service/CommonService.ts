import { Injectable } from '@angular/core';
import { Platform, ModalController } from '@ionic/angular';
import { ModalPopComponent } from '../shared-modules/modal-pop/modal-pop.component';
import { ModalPopArrayComponent } from '../shared-modules/modal-pop-array/modal-pop-array.component';
import { ModalSliderComponent } from '../util/modal-slider.component';

@Injectable({
    providedIn: 'root'
})
export class CommonService {

    constructor(
        private platform: Platform,
        private modalCtrl: ModalController
    ) { }

    /** Detects if the current platform is desktop */
    public isDesktop(): boolean {
        return this.platform.is('desktop');
    }

    /**
     * Recursively flattens an object and formats special fields
     * like timestamps into readable strings.
     */
    public flatten(obj: any, parentKey: string = ''): any[] {
        const items: any[] = [];

        const convertToGMT8 = (epoch: number): string => {
            return new Date(epoch * 1000).toLocaleString('en-US', {
                timeZone: 'Asia/Kuala_Lumpur'
            });
        };

        for (const key of Object.keys(obj)) {
            const newKey = parentKey ? `${parentKey}.${key}` : key;
            const value = obj[key];

            if (value && typeof value === 'object' && !Array.isArray(value)) {
                items.push(...this.flatten(value, newKey));
            } else {
                let displayValue = value;

                if (['exp', 'auth_time', 'iat'].includes(newKey) && typeof value === 'number') {
                    displayValue = convertToGMT8(value);
                } else if (Array.isArray(value)) {
                    displayValue = value.join(', ');
                }

                items.push({ key: newKey, value: displayValue });
            }
        }

        return items;
    }

    /**
     * Opens a modal with key-value data display
     */
    public async openPopModal(title: string, message: string, data: any) {
       await this.createModal(ModalPopComponent, title, message, data, 'wide-modal');
    }

    /**
     * Opens a modal that shows array-based data
     */
    public async openPopArrayModal(title: string, message: string, data: any) {
        await this.createModal(ModalPopArrayComponent, title, message, data);
    }

    /**
     * Opens a sliding modal with predefined breakpoints
     */
    public async openSlideModal(): Promise<void> {
        const modal = await this.modalCtrl.create({
            component: ModalSliderComponent,
            initialBreakpoint: 0.5,
            breakpoints: [0, 0.3, 0.75],
            componentProps: {
                title: 'Any Feedback?' // passing the data object to the modal component
            },
        });

        await modal.present();
        await modal.onWillDismiss(); // capture dismiss if needed
    }

    public async openSlideAIModal(data: any): Promise<void> {
        const modal = await this.modalCtrl.create({
            component: ModalSliderComponent,
            componentProps: {
                title: 'AI Explaination' // passing the data object to the modal component
                ,data: data // passing the data object to the modal component
            },
            initialBreakpoint: 0.9,
            breakpoints: [0, 0.3, 0.75],
        });

        await modal.present();
        await modal.onWillDismiss(); // capture dismiss if needed
    }

    /**
     * Helper to create and present a modal
     */
    private async createModal(
        component: any,
        title: string,
        message: string,
        data: any,
        cssClass?: string
    ): Promise<void> {
        const modal = await this.modalCtrl.create({
            component,
            componentProps: { title, message, data },
            cssClass,
            backdropDismiss: false
        });

        await modal.present();
    }

}
