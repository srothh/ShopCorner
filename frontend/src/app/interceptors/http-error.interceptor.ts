import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';


@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error) => {
        console.log(error);
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 0:
              return throwError('Backend nicht erreichbar');
            case 400:
              let message = error.error.replace(/[a-zA-Z]*\.[a-zA-Z]*\s/, ' ');
              message = message.replace(/{Validation errors=\[/, '');
              message = message.slice(0, -2);
              return throwError(message);
            case 404:
              return throwError(error.error);
            case 500:
              return throwError('Serverprobleme');
            default:
              return throwError(error.error);
          }

        }

      })
    );
  }
}

