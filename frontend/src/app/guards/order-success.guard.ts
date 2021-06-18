import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderSuccessGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot): boolean {
    return route.queryParamMap.has('PayerID') && route.queryParamMap.has('paymentId');
  }
}
