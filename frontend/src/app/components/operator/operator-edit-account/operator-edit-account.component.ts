import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {OperatorService} from '../../../services/operator/operator.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-edit-account',
  templateUrl: './operator-edit-account.component.html',
  styleUrls: ['./operator-edit-account.component.scss']
})
export class OperatorEditAccountComponent implements OnInit {
  user: string;
  operator: Operator;
  password = 'unchanged';
  updatedPassword: '';

  updateForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder, private router: Router, private authenticationService: OperatorAuthService,
              private operatorService: OperatorService) {
  }

  ngOnInit(): void {
    this.user = this.authenticationService.getUser();
    this.operatorService.getOperatorByLoginName(this.user)
      .subscribe(operator => this.operator = operator, error => {
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      }, () => {
        this.updateForm = this.formBuilder.group({
          name: [this.operator.name, [Validators.required]],
          loginName: [this.operator.loginName, [Validators.required]],
          oldPassword: [''],
          newPassword: ['', [Validators.minLength(8)]],
          email: [this.operator.email, [Validators.required]],
        });
      });
  }

  vanishError() {
    this.error = false;
  }

  save(): void {
    if (this.updateForm.valid) {
      this.operator = new Operator(this.operator.id, this.updateForm.controls.name.value,
        this.updateForm.controls.loginName.value,
        this.password, this.updateForm.controls.email.value,
        this.operator.permissions);

      this.password = this.updateForm.controls.oldPassword.value;
      this.updatedPassword = this.updateForm.controls.newPassword.value;

      if (this.password !== '' && this.updatedPassword !== '') {
        this.operatorService.updatePassword(this.password, this.updatedPassword).subscribe(
          () => {}, error => {
            console.log(error);
            this.error = true;
            if (typeof error.error === 'object') {
              this.errorMessage = error.error.error;
            } else {
              this.errorMessage = error.error;
            }
          }, () => {
           this.editData();
          }
        );
      } else {
      this.editData();
      }
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Updates user data not related to the password.
   *
   * @private
   */
  private editData() {

    this.operatorService.updateOperator(this.operator).subscribe(() => {
      this.submitted = true;
    }, error => {
      console.log(error);
      this.error = true;
      if (typeof error.error === 'object') {
        this.errorMessage = error.error.error;
      } else {
        this.errorMessage = error.error;
      }
    }, () => {
      if (this.operator.loginName !== this.user) {
        this.authenticationService.logoutUser();
        this.router.navigate(['/operator/login']);
      } else {
        this.router.navigate(['/operator/home']);
      }
    });
  }
}
