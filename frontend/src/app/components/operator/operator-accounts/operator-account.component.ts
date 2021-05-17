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
  admins: Operator[];
  employees: Operator[];
  page = 1;
  pageSize = 15;
  collectionSize = 0;
  showAdmins = true;

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

  nextPage() {
    if (this.page*this.pageSize<this.collectionSize){
      this.page += 1;
    }
  }

  previousPage() {
    if (this.page>1){
      this.page -= 1;
    }
  }

  private loadAllOperators() {
    this.operatorService.getAllOperators().subscribe(
      (operators: Operator[]) => {
        this.admins = operators.filter(i => i.permissions === 'admin');
        this.employees = operators.filter(i => i.permissions === 'employee');
        this.collectionSize=this.admins.length;
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
