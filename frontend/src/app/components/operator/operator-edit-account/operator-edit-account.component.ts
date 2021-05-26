import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Permissions} from '../../../dtos/permissions.enum';
import {Router} from '@angular/router';
import {OperatorService} from '../../../services/operator.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';


@Component({
  selector: 'app-operator-edit-account',
  templateUrl: './operator-edit-account.component.html',
  styleUrls: ['./operator-edit-account.component.scss']
})
export class OperatorEditAccountComponent implements OnInit {

  user: string;
  operator: Operator;

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
          password: ['unchanged', [Validators.required, Validators.minLength(8)]],
          email: [this.operator.email, [Validators.required]],
        });
      });
  }

  vanishError() {
    this.error = false;
  }

  save(): void{

    if (this.updateForm.valid) {

      const operator: Operator = new Operator(this.operator.id, this.updateForm.controls.name.value,
        this.updateForm.controls.loginName.value,
        this.updateForm.controls.password.value, this.updateForm.controls.email.value,
        this.operator.permissions);

      this.operatorService.updateOperator(operator).subscribe(() => {
        this.submitted = true;
        this.router.navigate(['/operator/home']);
      }, error => {
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      });
    } else {
      console.log('Invalid input');
    }

  }
}
