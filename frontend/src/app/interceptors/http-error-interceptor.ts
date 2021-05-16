import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';


@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error) => {
        console.log(error.error);
        let message = error.error.replace(/[a-zA-Z]*\.[a-zA-Z]*\s/, ' ');
        message = message.replace(/{Validation errors=\[/,'');
        console.log(message);
        message = message.slice(0, -2);
        return throwError(message
        );
      })
    );
  }
}

