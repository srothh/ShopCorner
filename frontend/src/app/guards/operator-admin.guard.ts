import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {CustomerAuthService} from '../services/auth/customer-auth.service';
import {OperatorAuthService} from '../services/auth/operator-auth.service';

@Injectable({
  providedIn: 'root'
})
export class OperatorAdminGuard implements CanActivate {
  constructor(private operatorAuthService: OperatorAuthService,
              private router: Router) {
  }

  canActivate(): boolean {
    if (this.operatorAuthService.getUserRole() === 'ADMIN') {
      return true;
    } else {
      this.router.navigate(['/operator/home']);
      return false;
    }
  }
}
