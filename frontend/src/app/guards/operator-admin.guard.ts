import { Injectable } from '@angular/core';
import {CanActivate, Router} from '@angular/router';
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
