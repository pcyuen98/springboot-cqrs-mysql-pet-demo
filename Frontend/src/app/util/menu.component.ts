import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ModuleInfo } from '../models/module.name';
import { Router } from '@angular/router';
import { UserService } from '../service/UserService';

@Component({
    selector: 'app-menu',
    template: `
              <span *ngFor="let item of getMenuItems()" (click)="onMenuItemClick(item)" style="font-size: 0.9rem;">
                <span *ngIf="hasRole(item.role)" >

                <a *ngIf="!isSameURL(item)" class="font-link clickable logout-green"><b>{{ item.name }}
                    | </b> </a>
                <span *ngIf="isSameURL(item)" class="logout-white" style="font-size: 0.9rem;"><b>{{ item.name }} | </b> </span>
                </span>
              </span>
  `,
    styles: [`
  `]
})
export class MenuComponent {

    constructor(
        private router: Router,
        private userService: UserService,
    ) { }

    getMenuItems() {
        return Object.values(ModuleInfo)
    }

    getURLWithoutParam() {
        const parts = this.router.url.split('?')[0].split('/');
        return parts.slice(0, 2).join('/');

    }

    isSameURL(item: any) {
        const currentPath = this.getURLWithoutParam()
        return currentPath === item.path;
    }

    hasRole(role: string) {
        if (role != undefined) {
            return this.userService.hasRole(role)
        }
        else {
            return true
        }
    }

    onMenuItemClick(item: any) {

        // Use the Router service to navigate.
        if (item.params) {
            this.router.navigate([item.path, item.params]).then(() => {}); // Navigate with parameters
        } else if (item.queryParams) {
            this.router.navigate([item.path], {
                queryParams: item.queryParams,
            }).then(() => {}); // Navigate with query parameters
        } else {
            this.hasRole(item.role)
            this.router.navigate([item.path]).then(() => {}); // Navigate without extra parameters
        }
    }
}

@NgModule({
    declarations: [MenuComponent],
    imports: [CommonModule, IonicModule],
    exports: [MenuComponent]
})
export class MenuModule { }
