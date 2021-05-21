import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Permissions} from '../../../dtos/permissions.enum';
import {Router} from '@angular/router';
import {OperatorService} from '../../../services/operator.service';

@Component({
  selector: 'app-operator-edit-account',
  templateUrl: './operator-edit-account.component.html',
  styleUrls: ['./operator-edit-account.component.scss']
})
export class OperatorEditAccountComponent implements OnInit {

  // TODO replace with currently logged in operator
  operator = new Operator(1, 'test', 'test', 'password', 'test@mail.com', Permissions.employee);

  updateForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder, private router: Router,
              private operatorService: OperatorService) {
    this.updateForm = this.formBuilder.group({
      name: [this.operator.name, [Validators.required]],
      loginName: [this.operator.loginName, [Validators.required]],
      password: [this.operator.password, [Validators.required, Validators.minLength(8)]],
      email: [this.operator.email, [Validators.required]],

    });
  }

  ngOnInit(): void {
    //TODO fetch currently logged in operator from backend
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
