import { Component, OnInit } from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {Permissions} from '../../../dtos/permissions.enum';
import {Router} from '@angular/router';

@Component({
  selector: 'app-operator-home',
  templateUrl: './operator-home.component.html',
  styleUrls: ['./operator-home.component.scss']
})
export class OperatorHomeComponent implements OnInit {

  // TODO replace with currently logged in operator
  operator = new Operator(1, 'test', 'test', 'password', 'test@mail.com', Permissions.employee);


  submitted = false;
  error = false;
  errorMessage = '';

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    //TODO fetch currently logged in operator from backend
  }

  goToEdit() {
    this.router.navigate(['/operator/account/edit']);
  };

}
