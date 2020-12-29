import {Injectable} from '@angular/core';
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

import {AccountService} from '@app/services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private accountService: AccountService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next
            .handle(request)
            .pipe(catchError(this.handleError));
    }

    private handleError(error: HttpErrorResponse) {

        let err: string;

        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            err = `Error: ${error.error.message}`;

            if (err.trim() === 'Unknown Error') {
                err = "Can't reach the server, check your connection and try again.";
            }

            console.error('An error occurred:', err);

        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            if ([401, 403].includes(error.status) && this.accountService.userValue) {
                // auto logout if 401 or 403 response returned from api
                this.accountService.logout();
            }

            err = ErrorInterceptor.getServerErrorMessage(error);
            console.error(`Backend returned code ${error.status}, body was: ${ErrorInterceptor
                .getErrorDetails(error.error.details)}`);
        }

        // Return an observable with a user-facing error message.
        return throwError(err);
    }

    private static getServerErrorMessage(error: HttpErrorResponse): string {

        let details: string = ErrorInterceptor.getErrorDetails(error.error.details);

        switch (error.status) {
            case 400: {
                return `Validation failed: [${details}]`;
            }
            case 404: {
                return `Not Found: ${error.error.details}`;
            }
            case 500: {
                return `Internal Server Error: ${error.error.details}`;
            }
            default: {
                return `Unknown Server Error: ${error.error.message}; ${details}`;
            }
        }
    }

    private static getErrorDetails(details: ErrorDetail[]): string {
        let detailMessage: string = "";

        details.forEach((detail) => {
            detailMessage += `("${detail.field}": ${detail.errorMessage})`;
        });

        return detailMessage;
    }
}

export class ErrorDetail {
    field: string;
    errorMessage: string;

    constructor(field: string, errorMessage: string){
        this.field = field;
        this.errorMessage = errorMessage;
    }
}
