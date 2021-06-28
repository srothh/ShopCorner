import {Component, OnInit} from '@angular/core';
import {Operator} from '../../../dtos/operator';
import {OperatorService} from '../../../services/operator/operator.service';
import {Permissions} from '../../../dtos/permissions.enum';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {Pagination} from '../../../dtos/pagination';
import {Router} from '@angular/router';

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

  constructor(private authService: OperatorAuthService,
              private operatorService: OperatorService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.selected = [];
    if (this.authService.getUserRole() === 'EMPLOYEE') {
      this.permissions = Permissions.employee;
    }
    this.loadOperatorsPage();
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
    this.page = 0;
    this.loadOperatorsPage();
  }

  /**
   * changes view to admins
   */
  showAdmins(): void {
    this.selected = [];
    this.permissions = Permissions.admin;
    this.currentCollectionSize = this.collectionSizeAdmin;
    this.page = 0;
    this.loadOperatorsPage();
  }

  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if ((this.page + 1) * this.pageSize < this.currentCollectionSize) {
      this.page += 1;
      this.loadOperatorsPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.loadOperatorsPage();
    }
  }

  /**
   * selects or deselects operator
   *
   * @param operator that should be selescted or deselected
   */
  selectOperator(operator: Operator) {
    if (this.getPermission() === 'ADMIN') {
      if (this.selected.includes(operator)) {
        const index = this.selected.indexOf(operator, 0);
        this.selected.splice(index, 1);
      } else {
        this.selected.push(operator);
      }
    }
  }

  /**
   * calls on service to delete selected operators and after last delete reloads page
   */
  deleteOperator() {
    for (const operator of this.selected) {
      this.operatorService.deleteOperator(operator.id).subscribe(
        () => {
          if (this.selected.indexOf(operator) === this.selected.length - 1) {
            if ((this.page + 1) * this.pageSize >= this.currentCollectionSize && this.operators.length === this.selected.length
              && this.page > 0) {
              this.previousPage();
            } else {
              this.loadOperatorsPage();
            }
            if (operator.permissions === Permissions.employee) {
              this.collectionSizeEmployee -= this.selected.length;
              this.currentCollectionSize = this.collectionSizeEmployee;
              this.selected = [];
            } else {
              this.collectionSizeAdmin -= this.selected.length;
              this.currentCollectionSize = this.collectionSizeAdmin;
              this.selected = [];
            }
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
   * calls on service to change the permissions of the selected operators and after last change reloads page
   */
  changePermissions() {
    for (const operator of this.selected) {
      this.operatorService.changePermissions(operator).subscribe(
        () => {
          if (this.selected.indexOf(operator) === this.selected.length - 1) {
            if ((this.page + 1) * this.pageSize >= this.currentCollectionSize && this.operators.length === this.selected.length
              && this.page > 0) {
              this.previousPage();
            } else {
              this.loadOperatorsPage();
            }
            if (operator.permissions === Permissions.employee) {
              this.collectionSizeEmployee -= this.selected.length;
              this.currentCollectionSize = this.collectionSizeEmployee;
              this.selected = [];
            } else {
              this.collectionSizeAdmin -= this.selected.length;
              this.currentCollectionSize = this.collectionSizeAdmin;
              this.selected = [];
            }
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
   * Redirects to the employee registration page
   * User needs to be an admin
   */
  registerEmployee() {
    this.router.navigate(['operator/registration']);
  }

  /**
   * calls on Service class to fetch all operator accounts from backend
   */
  private loadOperatorsPage() {
    this.operatorService.getOperatorsPage(this.page, this.pageSize, this.permissions).subscribe(
      (page: Pagination<Operator>) => {
        this.operators = page.items;
        this.currentCollectionSize = page.totalItemCount;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

}
