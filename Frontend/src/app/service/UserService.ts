import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { User } from '../models/user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    /**
     * Set or update the user cookie with merged values.
     */
    public setUserCookie(userInput: User): void {
        try {
            const existingUser = this.getUserCookie();
            const mergedUser: User = { ...new User(), ...existingUser, ...userInput };

            Cookie.set('user', JSON.stringify(mergedUser), 3650); // 10 years
            console.log('[UserService] User stored →', JSON.stringify(mergedUser));
        } catch (error) {
            console.error('[UserService] Error setting user cookie →', error);
        }
    }

    /**
     * Retrieve the user object from cookie.
     */
    public getUserCookie(): User {
        try {
            const userCookie = Cookie.get('user');
            return JSON.parse(userCookie);
        } catch {
            return new User();
        }
    }

    /**
     * Check if user is logged in based on access token.
     */
    public isLogin(): boolean {
        return !!Cookie.get('access_token');
    }

    /**
     * Decode and return the token payload.
     */
    public getTokenData(): any {
        const token = Cookie.get('access_token');
        if (!token) return undefined;

        const payload = this.decodeTokenPayload(token);
        return payload ? { ...payload } : undefined;
    }

    public hasRole(role: any): boolean {
        const payload = this.getTokenData();

        const matchingValues = payload.resource_access.springback.roles.filter((value: any) => role.includes(value));
        return matchingValues.length > 0;
    }

    /**
     * Decode JWT token and return its payload.
     */
    private decodeTokenPayload(token: string): any {
        try {
            const payloadBase64 = token.split('.')[1];
            const decoded = atob(payloadBase64);
            return JSON.parse(decoded);
        } catch {
            return null;
        }
    }

    /**
     * Retrieve all cookies as name-value pairs.
     */
    public getAllCookies(): { name: string; value: string }[] {
        return document.cookie.split(';')
            .map(cookie => cookie.trim().split('='))
            .filter(([name, value]) => name && value !== undefined)
            .map(([name, value]) => ({ name, value }))
            .map(({ name, value }) => ({ name: name.trim(), value: value.trim() }));
    }

    /**
     * Check if JWT token is expired.
     */
    public isTokenExpired(): boolean {
        try {
            const tokenPayload = this.getTokenData();
            const exp = tokenPayload?.exp;
            const now = Math.floor(Date.now() / 1000);

            if (exp && exp > now) {
                return false;
            }

            alert('Token expired. Please log out.');
            return true;
        } catch {
            alert('Invalid token or cookie.');
            return true;
        }
    }

}
