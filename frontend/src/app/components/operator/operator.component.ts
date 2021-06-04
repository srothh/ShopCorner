import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {OperatorAuthService} from '../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator',
  templateUrl: './operator.component.html',
  styleUrls: ['./operator.component.scss']
})
export class OperatorComponent implements OnInit {

  user: string;
  role: string;

  constructor(private authService: OperatorAuthService, private router: Router) {
    this.role = authService.getUserRole();
  }

  logoutUser() {
    this.authService.logoutUser();
    this.router.navigate(['/operator/login']);
  }

  ngOnInit(): void {
    this.user = this.authService.getUser();
  }

}
