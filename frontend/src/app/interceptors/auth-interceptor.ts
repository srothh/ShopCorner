import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {CustomerAuthService} from '../services/customer-auth.service';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: CustomerAuthService, private globals: Globals) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const customerAuthUri = this.globals.backendUri + '/authentication/customer';
    const registrationUri = this.globals.backendUri + '/customers';
    const addressUri = this.globals.backendUri + '/address';
    if (req.url === customerAuthUri || (req.method === 'POST' && req.url === registrationUri)
      || (req.method === 'POST' && req.url === addressUri)) {
      return next.handle(req);
    }

    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + this.authService.getToken())
    });

    return next.handle(authReq);
  }
}
