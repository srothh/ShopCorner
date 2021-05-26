import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {CustomerAuthService} from '../services/auth/customer-auth.service';
import {OperatorAuthService} from '../services/auth/operator-auth.service';

/**
 * Prevents an operator to access the login page, if the operator is already logged in
 * If the operator is already logged in, they will be redirected to the /operator/home page
 *
 */
@Injectable({
  providedIn: 'root'
})
export class PreventOperatorLoginGuard implements CanActivate {

  constructor(private operatorAuthService: OperatorAuthService,
              private router: Router) {
  }

  canActivate(): boolean {
    if (this.operatorAuthService.isLoggedIn()) {
      this.router.navigate(['/operator/home']);
      return false;
    } else {
      return true;
    }
  }

}
