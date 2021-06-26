import { Component, OnInit } from '@angular/core';
import {Operator} from '../../../dtos/operator';
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
  error = false;
  errorMessage = '';

  constructor(private router: Router, private authenticationService: OperatorAuthService,
              private operatorService: OperatorService) {
  }

  ngOnInit(): void {
    this.user = this.authenticationService.getUser();

    this.operatorService.getOperatorByLoginName(this.user)
      .subscribe(operator => this.operator = operator, (error) => {
        console.log(error);
        this.error = true;
        this.errorMessage = error;
      });
  }

  goToEdit() {
    this.router.navigate(['/operator/account/edit']);
  }

  vanishError() {
    this.error = false;
  }

}
