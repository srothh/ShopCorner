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
  end = new Date();
  start = new Date();
  loaded = false;

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.start.setDate(this.start.getDate()-90);
    this.loadInvoicesForTime();
  }

  update() {
    this.loaded = false;
    this.start = new Date(this.start);
    this.end = new Date(this.end);
    this.loadInvoicesForTime();
  }

  getMonth(month: string) {
    this.loaded = false;
    this.start =  new Date(month);
    this.end = new Date(this.start);
    this.end.setMonth(this.end.getMonth()+1);
    this.end.setDate(this.end.getDate()-1);
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
