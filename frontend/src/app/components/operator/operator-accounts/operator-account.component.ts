import { Component, OnInit } from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {OperatorService} from '../../../services/operator-service';

@Component({
  selector: 'app-operator-accounts',
  templateUrl: './operator-account.component.html',
  styleUrls: ['./operator-account.component.scss']
})
export class OperatorAccountComponent implements OnInit {

  error = false;
  errorMessage = '';
  successMessage = '';
  success = false;
  operators: Operator[];

  constructor(private operatorService: OperatorService) {
  }

  ngOnInit(): void {
    this.loadAllOperators();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  private loadAllOperators() {
    this.operatorService.getAllOperators().subscribe(
      (operators: Operator[]) => {
        this.operators = operators;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
}
