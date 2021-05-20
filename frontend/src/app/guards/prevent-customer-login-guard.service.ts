import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {CustomerAuthService} from '../services/customer-auth.service';


/**
 * Prevents a customer to access the login page, if the customer is already logged in
 * If the customer is already logged in, they will be redirected to the /account page
 *
 */
@Injectable({
  providedIn: 'root'
})
export class PreventCustomerLoginGuard implements CanActivate {

  constructor(private customerAuthService: CustomerAuthService,
              private router: Router) {
  }

  canActivate(): boolean {
    if (this.customerAuthService.isLoggedIn()) {
      this.router.navigate(['/account']);
      return false;
    } else {
      return true;
    }
  }

}
