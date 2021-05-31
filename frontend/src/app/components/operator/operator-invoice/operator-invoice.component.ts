import {Component, OnInit} from '@angular/core';
import {forkJoin} from 'rxjs';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';
import {Pagination} from '../../../dtos/pagination';

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
  error = false;
  errorMessage = '';

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit(): void {
    this.loadInvoicesForPage();
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
      this.loadInvoicesForPage();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.loadInvoicesForPage();
    }
  }

  getDetaildView(id: number) {
    console.log(id);
  }


  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadInvoicesForPage() {
    this.invoiceService.getAllInvoicesForPage(this.page, this.pageSize).subscribe(
      (paginationDto: Pagination<Invoice>) => {
        console.log(paginationDto);
        this.invoices = paginationDto.items;
        this.collectionSize = paginationDto.totalItemCount;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
