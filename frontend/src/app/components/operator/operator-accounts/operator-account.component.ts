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

  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if (this.page*this.pageSize<this.collectionSize){
      this.page += 1;
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page>1){
      this.page -= 1;
    }
  }

  /**
   * calls on Service class to fetch all operator accounts from backend
   */
  private loadAllOperators() {
    this.operatorService.getAllOperators().subscribe(
      (operators: Operator[]) => {
        this.admins = operators.filter(i => i.permissions === 'admin');
        this.employees = operators.filter(i => i.permissions === 'employee');
        this.collectionSize=this.admins.length;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
