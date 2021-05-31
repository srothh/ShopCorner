import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';


@Component({
  selector: 'app-shop-login',
  templateUrl: './shop-login.component.html',
  styleUrls: ['./shop-login.component.scss']
})
export class ShopLoginComponent implements OnInit {
  // Redirect path after successful login
  redirectPath = '/home';

  constructor(public authService: CustomerAuthService) {
  }

  ngOnInit() {
  }

}
