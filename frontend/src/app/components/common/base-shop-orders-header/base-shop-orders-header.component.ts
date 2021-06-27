import {Component, Input, OnInit} from '@angular/core';
import {Order} from '../../../dtos/order';
import {InvoiceService} from '../../../services/invoice.service';
import {MeService} from '../../../services/me.service';

@Component({
  selector: 'app-base-shop-orders-header',
  templateUrl: './base-shop-orders-header.component.html',
  styleUrls: ['./base-shop-orders-header.component.scss']
})
export class BaseShopOrdersHeaderComponent implements OnInit {
  @Input()
  order: Order;
  error: boolean;
  errorMessage: boolean;
  constructor(private meService: MeService) { }

  ngOnInit(): void {
  }
  downloadInvoice(event: Event, invoiceId: number, invoiceDate: string) {
    event.preventDefault();
    this.meService.getInvoiceAsPdfByIdForCustomer(invoiceId).subscribe((data) => {
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + invoiceDate + '_' + invoiceId + '.pdf';
      link.click();
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

}
