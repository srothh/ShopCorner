import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {CustomerAuthService} from '../services/customer-auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerAuthGuard implements CanActivate {

  constructor(private customerAuthService: CustomerAuthService,
              private router: Router) {}

  canActivate(): boolean {
    if (this.customerAuthService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
