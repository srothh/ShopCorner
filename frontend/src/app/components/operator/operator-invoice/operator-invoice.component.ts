import { Component, OnInit } from '@angular/core';
import {forkJoin} from 'rxjs';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';

@Component({
  selector: 'app-operator-invoice',
  templateUrl: './operator-invoice.component.html',
  styleUrls: ['./operator-invoice.component.scss']
})
export class OperatorInvoiceComponent implements OnInit {
  toggle = false;
  invoices: Invoice[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;


  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.fetchData();
  }

  toggleSide() {
    this.toggle = !this.toggle;
    console.log(this.toggle);
  }


  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      //this.loadInvoicesForPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      //this.loadInvoicesForPage();
    }
  }


  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  /*private loadInvoicesForPage() {
    this.customerService.getAllCustomersForPage(this.page, this.pageSize).subscribe(
      (paginationDto: Pagination<Customer>) => {
        console.log(paginationDto);
        this.customers = paginationDto.items;
        this.collectionSize = paginationDto.totalItemCount;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }*/




  private fetchData(): void {
    forkJoin([this.invoiceService.getInvoice()])
      .subscribe(([invoices]) => {
        this.invoices = invoices;
      });
  }

}
