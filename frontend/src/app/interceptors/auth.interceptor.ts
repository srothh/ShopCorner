import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {CustomerAuthService} from '../services/auth/customer-auth.service';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {OperatorAuthService} from '../services/auth/operator-auth.service';

enum AuthActor {
  operator,
  customer
}

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private customerAuthService: CustomerAuthService,
              private operatorAuthService: OperatorAuthService,
              private globals: Globals) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const customerAuthUri = this.globals.backendUri + '/authentication/customers';
    const registrationUri = this.globals.backendUri + '/customers';
    const addressUri = this.globals.backendUri + '/address';
    if (req.url === customerAuthUri || (req.method === 'POST' && req.url === registrationUri)
      || (req.method === 'POST' && req.url === addressUri)) {
      return next.handle(req);
    }
    return next.handle(req);
  }
}
