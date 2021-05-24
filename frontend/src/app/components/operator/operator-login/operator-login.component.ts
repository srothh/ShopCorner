import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthRequest} from '../../../dtos/auth-request';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-login',
  templateUrl: './operator-login.component.html',
  styleUrls: ['./operator-login.component.scss']
})
export class OperatorLoginComponent implements OnInit {
  loginForm: FormGroup;

  // Redirect path after successful login
  redirectPath = '/operator/home';

  // After first submission attempt, form validation will start
  submitted = false;

  constructor(private formBuilder: FormBuilder, public authService: OperatorAuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit() {
  }

}
