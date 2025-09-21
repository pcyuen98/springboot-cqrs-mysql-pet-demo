import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { UserService } from './UserService';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(
        private userService: UserService,
        private router: Router,
    ) { }


    async canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {

        const isLoggedIn = this.userService.isLogin();
        const { roles } = route.data;

        let isUserHasPermission = undefined
        if (isLoggedIn) {
            isUserHasPermission = this.userService.hasRole(roles)
        }

        if (!isLoggedIn || !isUserHasPermission) {
            alert('Login not found or unauthorized access')

            await this.router.navigate(['/home']);
            return false
        }

        return true; // User is authenticated
    }

}
