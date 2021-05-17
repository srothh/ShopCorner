import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private globals: Globals) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authUri = this.globals.backendUri + '/authentication';
    const registrationUri = this.globals.backendUri + '/customers';
    const addressUri = this.globals.backendUri + '/address';
    if (req.url === authUri || (req.method === 'POST' && req.url === registrationUri)
      || (req.method === 'POST' && req.url === addressUri)) {
      return next.handle(req);
    }

    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + this.authService.getToken())
    });

    return next.handle(authReq);
  }
}
