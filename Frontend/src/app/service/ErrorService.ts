import { Injectable } from "@angular/core";
import { GlobalConstants } from "src/environments/GlobalConstants";
import { ErrorApp } from "../models/error-app";

const errorMessages: { [code: number]: string } = {
  401: 'Unauthorized Access',
  403: 'Access Denied or Insufficient Permission',
};

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  handleError(message: string, caused: string, error: any): void {
    if (!error) {
      alert('Error object is required.');
      console.warn('handleError was called with an undefined or null error.');
      return;
    }

    const errorApp = new ErrorApp();
    error.statusText = errorMessages[error.status] || error.message;

    if (error.statusText) {
      errorApp.appMessage = message + ". " + error.statusText;
    }
    else {
      errorApp.appMessage = message
    }

    errorApp.caused = caused;
    errorApp.code = error.status
    errorApp.error = error;

    GlobalConstants.globalBEError = errorApp;

    console.error(`[ErrorService] ${message} | Type: ${caused} | Error:`, error);
  }

}