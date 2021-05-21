import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {OperatorService} from '../../../services/operator.service';
import {Permissions} from '../../../dtos/permissions.enum';

@Component({
  selector: 'app-operator-accounts',
  templateUrl: './operator-account.component.html',
  styleUrls: ['./operator-account.component.scss']
})
export class OperatorAccountComponent implements OnInit {

  error = false;
  errorMessage = '';
  operators: Operator[];
  page = 0;
  pageSize = 15;
  currentCollectionSize = 0;
  collectionSizeAdmin = 0;
  collectionSizeEmployee = 0;
  permissions = Permissions.admin;

  constructor(private operatorService: OperatorService) {
  }

  ngOnInit(): void {
    this.loadOperatorsPage();
    this.loadOperatorCount();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * changes view to employees
   */
  showEmployees(): void {
    this.permissions = Permissions.employee;
    this.currentCollectionSize = this.collectionSizeEmployee;
    this.loadOperatorsPage();
  }

  /**
   * changes view to admins
   */
  showAdmins(): void {
    this.permissions = Permissions.admin;
    this.currentCollectionSize = this.collectionSizeAdmin;
    this.loadOperatorsPage();
  }

  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if ((this.page+1)*this.pageSize<this.currentCollectionSize){
      this.page += 1;
      this.loadOperatorsPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page>0){
      this.page -= 1;
      this.loadOperatorsPage();
    }
  }

  /**
   * calls on Service class to fetch all operator accounts from backend
   */
  private loadOperatorsPage() {
    this.operatorService.getOperatorsPage(this.page, this.pageSize, this.permissions).subscribe(
      (operators: Operator[]) => {
        this.operators = operators;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  /**
   * calls on Service class to fetch operator count from backend
   */
  private loadOperatorCount() {
    this.operatorService.getOperatorCount().subscribe(
      (count: number[]) => {
        this.collectionSizeAdmin = count[0];
        this.collectionSizeEmployee = count[1];
        this.currentCollectionSize = this.collectionSizeAdmin;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
