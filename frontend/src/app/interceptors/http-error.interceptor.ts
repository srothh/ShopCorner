import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';


@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse) {
          if(error.status === 0){
            return throwError('backend unreachable');
          }else if(error.status === 400){
            let message = error.error.replace(/[a-zA-Z]*\.[a-zA-Z]*\s/, ' ');
            message = message.replace(/{Validation errors=\[/, '');
            message = message.slice(0, -2);
            return throwError(message
            );
          }else{
            return throwError(error.error
            );
          }

        }

      })
    );
  }
}

