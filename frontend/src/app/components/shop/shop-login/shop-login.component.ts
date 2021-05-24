import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {AuthRequest} from '../../../dtos/auth-request';


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
