import {Component, OnInit} from '@angular/core';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';
import {Pagination} from '../../../dtos/pagination';
import {InvoiceType} from '../../../dtos/invoiceType.enum';

@Component({
  selector: 'app-operator-invoice',
  templateUrl: './operator-invoice.component.html',
  styleUrls: ['./operator-invoice.component.scss']
})
export class OperatorInvoiceComponent implements OnInit {
  toggleForm = false;
  toggleDetailView = false;

  invoices: Invoice[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  error = false;
  errorMessage = '';
  detailViewInvoice: Invoice;
  invoiceType = InvoiceType.operator;

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit(): void {
    this.loadInvoicesForPage();
  }

  toggleSide() {
    if (!this.toggleDetailView) {
      this.toggleForm = !this.toggleForm;
    } else {
      this.toggleForm = false;
    }
    this.toggleDetailView = false;
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

  /**
   * toggles to the detailView.
   *
   * @param invoice destination invoice
   */
  getDetailedView(invoice: Invoice) {
    this.toggleDetailView = true;
    this.toggleForm = false;
    this.detailViewInvoice = invoice;
  }


  showAll() {
    this.invoiceType = InvoiceType.operator;
    this.invoices = [];
    this.loadInvoicesForPage();
  }

  showCustomer() {
    this.invoiceType = InvoiceType.customer;
    this.invoices = [];
    this.loadInvoicesForPage();
  }

  showCanceled() {
    console.log('TODO');
  }

  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadInvoicesForPage() {
    this.invoiceService.getAllInvoicesForPage(this.page, this.pageSize, this.invoiceType).subscribe(
      (paginationDto: Pagination<Invoice>) => {
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
