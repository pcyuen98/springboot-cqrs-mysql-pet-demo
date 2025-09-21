import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  constructor(private alertController: AlertController) {}

public async displayMsgBox(msg: string): Promise<string> {
  return new Promise(async (resolve) => {
    const alert = await this.alertController.create({
      header: 'Info',
      subHeader: '',
      message: msg,
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          handler: () => {
            resolve('cancel');
          },
        },
        {
          text: 'OK',
          handler: () => {
            resolve('ok');
          },
        },
      ],
    });

    await alert.present();
  });
}

public async okOnly(msg: string): Promise<string> {
  return new Promise(async (resolve) => {
    const alert = await this.alertController.create({
      header: 'Info',
      subHeader: '',
      message: msg,
      buttons: [
        {
          text: 'OK',
          handler: () => {
            resolve('ok');
          },
        },
      ],
    });

    await alert.present();
  });
}

}
