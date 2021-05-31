import { Component, OnInit } from '@angular/core';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {forkJoin} from 'rxjs';
import {InvoiceService} from '../../../services/invoice.service';

@Component({
  selector: 'app-operator-invoice-overview',
  templateUrl: './operator-invoice-overview.component.html',
  styleUrls: ['./operator-invoice-overview.component.scss']
})
export class OperatorInvoiceOverviewComponent implements OnInit {
  invoices: Invoice[];

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.fetchData();
  }

  private fetchData(): void {
    forkJoin([this.invoiceService.getInvoice()])
      .subscribe(([invoices]) => {
        this.invoices = invoices;
      });
  }

}
