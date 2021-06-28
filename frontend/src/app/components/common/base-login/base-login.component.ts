import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Route, Router} from '@angular/router';
import {AuthRequest} from '../../../dtos/auth-request';
import {IAuthService} from '../../../services/auth/interface-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './base-login.component.html',
  styleUrls: ['./base-login.component.scss']
})
export class BaseLoginComponent implements OnInit {

  @Input() public authService: IAuthService;
  @Input() public showRegistrationButton = true;
  @Input() public redirectPath = '';

  loginForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    if (this.loginForm.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
      this.authenticateUser(authRequest);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.loginName);
    this.authService.loginUser(authRequest).subscribe(
      () => {
        console.log('Successfully logged in user: ' + authRequest.loginName);
        this.router.navigate([this.redirectPath]);
      },
      error => {
        console.log('Could not log in due to:');
        this.error = true;
        this.errorMessage = error;
      }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  ngOnInit() {
  }


}
