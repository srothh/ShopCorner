import {Component, OnInit} from '@angular/core';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-login',
  templateUrl: './operator-login.component.html',
  styleUrls: ['./operator-login.component.scss']
})
export class OperatorLoginComponent implements OnInit {
  // Redirect path after successful login
  redirectPath = '/operator/home';

  constructor(public authService: OperatorAuthService) {
  }

  ngOnInit() {
  }

}
