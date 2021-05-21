import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../dtos/customer';
import {CustomerService} from '../../../services/customer.service';

@Component({
  selector: 'app-operator-customers',
  templateUrl: './operator-customer.component.html',
  styleUrls: ['./operator-customer.component.scss']
})
export class OperatorCustomerComponent implements OnInit {

  error = false;
  errorMessage = '';
  customers: Customer[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.loadCustomersForPage();
    this.getCustomerCount();
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
    if ((this.page+1)*this.pageSize<this.collectionSize){
      this.page += 1;
      this.loadCustomersForPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.loadCustomersForPage();
    }
  }

  /**
   * calls on Service class to fetch all operator accounts from backend
   */
  private loadCustomersForPage() {
    this.customerService.getAllCustomersForPage(this.page, this.pageSize).subscribe(
      (customers: Customer[]) => {
        this.customers = customers;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }

  private getCustomerCount() {
    this.customerService.getCustomerCount().subscribe(
      (count: number) => {
        this.collectionSize = count;
        console.log(this.collectionSize);

      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }


}
