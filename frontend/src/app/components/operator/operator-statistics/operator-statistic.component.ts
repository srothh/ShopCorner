import {Component, OnInit} from '@angular/core';
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
  start: Date;
  end: Date;
  loaded = false;

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.end = new Date();
    this.start = new Date();
    this.start.setDate(this.start.getDate() - 90);
    this.loadInvoicesForTime();
  }

  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadInvoicesForTime() {
    this.invoiceService.getAllInvoicesByDate(this.start, this.end).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
        this.loaded = true;
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
