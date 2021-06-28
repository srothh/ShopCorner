import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot} from '@angular/router';

/*A guard that prevents the customer from accessing the Order-Success Page without a valid Payment */
@Injectable({
  providedIn: 'root'
})
export class OrderSuccessGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot): boolean {
    return route.queryParamMap.has('PayerID') && route.queryParamMap.has('paymentId');
  }
}
