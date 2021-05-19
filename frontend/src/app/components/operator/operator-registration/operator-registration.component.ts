import {Component, OnInit} from '@angular/core';
import {OperatorService} from '../../../services/operator.service';
import {Operator} from '../../../dtos/operator';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';
import {Permissions} from '../../../dtos/permissions.enum';

@Component({
  selector: 'app-operator-registration',
  templateUrl: './operator-registration.component.html',
  styleUrls: ['./operator-registration.component.scss']
})
export class OperatorRegistrationComponent implements OnInit {

  permissions = ['Administrator', 'Mitarbeiter'];
  permission = Permissions.employee;

  registrationForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';


  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,
              private operatorService: OperatorService) {
    this.registrationForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      loginName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      email: ['', [Validators.required]],
      permissions: ['', [Validators.required]]
    });
  }



    ngOnInit(): void {

    }

    vanishError() {
      this.error = false;
    }


    register() {

      if (this.registrationForm.valid) {

        if(this.registrationForm.controls.permissions.value === 'Administrator') {
            this.permission = Permissions.admin;
        }

       const operator: Operator = new Operator(0, this.registrationForm.controls.name.value,
          this.registrationForm.controls.loginName.value,
          this.registrationForm.controls.password.value, this.registrationForm.controls.email.value,
          this.permission.toString());

        this.operatorService.createOperator(operator).subscribe(() => {
          this.submitted = true;
          this.router.navigate(['/operator/accounts']);
        }, error => {
          console.log(error);
          this.error = true;
          this.errorMessage = error.error;
        });
      } else {
        console.log('Invalid input');
      }
    }

}
