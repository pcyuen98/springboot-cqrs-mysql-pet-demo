import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        const customError = {
          status: error.status,
          message: error.error?.message || 'Unexpected error occurred',
          code: error.error?.code,
          statusText: error.error?.status,
          exception: error.error?.exception
        };
        console.error('Global error handler:', customError);
        return throwError(() => customError);
      })
    );
  }
}
