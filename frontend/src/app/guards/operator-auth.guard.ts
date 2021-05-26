import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {OperatorAuthService} from '../services/auth/operator-auth.service';

@Injectable({
  providedIn: 'root'
})
export class OperatorAuthGuard implements CanActivate {
  constructor(private operatorAuthService: OperatorAuthService,
              private router: Router) {
  }

  canActivate(): boolean {
    console.log(this.operatorAuthService.isLoggedIn());
    if (this.operatorAuthService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/operator/login']);
      return false;
    }
  }
}
