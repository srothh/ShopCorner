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
  loginForm: FormGroup;

  // Redirect path after successful login
  redirectPath = '/home';

  // After first submission attempt, form validation will start
  submitted = false;

  constructor(private formBuilder: FormBuilder, public authService: CustomerAuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit() {
  }

}
