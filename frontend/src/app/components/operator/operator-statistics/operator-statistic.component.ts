import {Component, OnInit} from '@angular/core';
import {Pagination} from '../../../dtos/pagination';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';
import {InvoiceType} from '../../../dtos/invoiceType.enum';

@Component({
  selector: 'app-operator-statistics',
  templateUrl: './operator-statistic.component.html',
  styleUrls: ['./operator-statistic.component.scss']
})
export class OperatorStatisticComponent implements OnInit {

  error = false;
  errorMessage = '';
  invoices: Invoice[];
  invoiceType = InvoiceType.operator;
  page = 0;
  pageSize = 40;
  loaded = false;

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.loadInvoicesForPage();
  }

  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadInvoicesForPage() {
    this.invoiceService.getAllInvoicesForPage(this.page, this.pageSize, this.invoiceType).subscribe(
      (paginationDto: Pagination<Invoice>) => {
        this.invoices = paginationDto.items;
        this.loaded = true;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
