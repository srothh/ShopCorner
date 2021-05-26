import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {OperatorService} from '../../../services/operator.service';
import {Permissions} from '../../../dtos/permissions.enum';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-accounts',
  templateUrl: './operator-account.component.html',
  styleUrls: ['./operator-account.component.scss']
})
export class OperatorAccountComponent implements OnInit {

  error = false;
  errorMessage = '';
  operators: Operator[];
  selected: Operator[];
  page = 0;
  pageSize = 15;
  currentCollectionSize = 0;
  collectionSizeAdmin = 0;
  collectionSizeEmployee = 0;
  permissions = Permissions.admin;

  constructor(private authService: OperatorAuthService, private operatorService: OperatorService) {
  }

  ngOnInit(): void {
    this.selected = [];
    if (this.authService.getUserRole() === 'EMPLOYEE') {
      this.permissions = Permissions.employee;
    }
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
   * calls on authentication service to return permission of logged in operator
   *
   * @return string role of logged in operator
   */
  getPermission(): string {
    return this.authService.getUserRole();
  }

  /**
   * changes view to employees
   */
  showEmployees(): void {
    this.selected = [];
    this.permissions = Permissions.employee;
    this.currentCollectionSize = this.collectionSizeEmployee;
    this.loadOperatorsPage();
  }

  /**
   * changes view to admins
   */
  showAdmins(): void {
    this.selected = [];
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
   * selects or deselects employee
   *
   * @param operator that should be selescted or deselected
   */
  selectOperator(operator: Operator) {
    if (this.selected.includes(operator)) {
      const index = this.selected.indexOf(operator, 0);
      this.selected.splice(index, 1);
    } else if (operator.permissions === 'employee') {
      this.selected.push(operator);
    }
  }

  /**
   * calls on service to delete selected operators and after last delete reloads page
   */
  deleteOperator() {
    for (const operator of this.selected) {
      this.operatorService.deleteOperator(operator.id).subscribe(
        () => {
          if (this.selected.indexOf(operator) === this.selected.length-1) {
            if ((this.page+1)*this.pageSize >= this.currentCollectionSize && this.operators.length === this.selected.length
              && this.page > 0){
              this.previousPage();
            } else {
              this.loadOperatorsPage();
            }
            this.collectionSizeEmployee -= this.selected.length;
            this.currentCollectionSize = this.collectionSizeEmployee;
            this.selected = [];
          }
        },
        error => {
          this.error = true;
          this.errorMessage = error.error;
        }
      );
    }
  }

  /**
   * calls on Service class to fetch all operator accounts from backend
   */
  private loadOperatorsPage() {
    this.operatorService.getOperatorsPage(this.page, this.pageSize, this.permissions).subscribe(
      (page: Operator[]) => {
        this.operators = page;
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
        this.collectionSizeEmployee = count[1];
        if (this.authService.getUserRole() === 'ADMIN') {
          this.collectionSizeAdmin = count[0];
          this.currentCollectionSize = this.collectionSizeAdmin;
        } else {
          this.currentCollectionSize = this.collectionSizeEmployee;
        }
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
