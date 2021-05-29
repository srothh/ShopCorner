import { Component, OnInit } from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {Permissions} from '../../../dtos/permissions.enum';
import {Router} from '@angular/router';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {OperatorService} from '../../../services/operator.service';

@Component({
  selector: 'app-operator-home',
  templateUrl: './operator-home.component.html',
  styleUrls: ['./operator-home.component.scss']
})
export class OperatorHomeComponent implements OnInit {


  operator: Operator;
  user: string;

  submitted = false;
  error = false;
  errorMessage = '';

  constructor(private router: Router, private authenticationService: OperatorAuthService,
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
      });
  }

  goToEdit() {
    this.router.navigate(['/operator/account/edit']);
  };

  vanishError() {
    this.error = false;
  }

}
