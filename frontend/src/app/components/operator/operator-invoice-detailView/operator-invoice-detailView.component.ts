import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice/invoice.service';
import {Customer} from '../../../dtos/customer';
import {Promotion} from '../../../dtos/promotion';

@Component({
  selector: 'app-operator-invoice-detail-view',
  templateUrl: './operator-invoice-detailView.component.html',
  styleUrls: ['./operator-invoice-detailView.component.scss']
})
export class OperatorInvoiceDetailViewComponent implements OnInit {
  @Input() value: Invoice;
  detailedInvoice: Invoice;
  customer: Customer = new Customer( 0, '', '', '', '', null, '');
  error = false;
  errorMessage = '';
  download = false;
  orderExists = false;
  promotionExists = false;
  address: string;
  promotion: Promotion;
  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.detailedInvoice = new Invoice();
    this.detailedInvoice.date = '';
    this.detailedInvoice.amount = 0;

    this.orderExists = (this.value.orderNumber !== undefined && this.value.orderNumber !== null);
    if (this.orderExists ) {
      this.fetchOrderData(this.value);
    } else {
      this.fetchInvoiceData(this.value.id);
    }
  }

  onSubmit(event) {
    if (event === 'show') {
      this.showInvoiceById();
    } else if (event === 'download') {
      this.downloadInvoiceById();
    }

  }

  vanishError() {
    this.error = false;
  }

  downloadInvoiceById() {
    this.invoiceService.getInvoiceAsPdfById(this.value.id ).subscribe((data) => {
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + this.value.date + '_' + this.value.id + '.pdf';
      link.click();
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  showInvoiceById() {
    this.invoiceService.getInvoiceAsPdfById(this.value.id ).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const blobURL = URL.createObjectURL(newBlob);
      window.open(blobURL);
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  private fetchInvoiceData(id: number) {
    this.invoiceService.getInvoiceById(id).subscribe( (item) => {
      this.detailedInvoice = item;

    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }
  private fetchOrderData(invoice: Invoice) {
    this.invoiceService.getOrderByOrderNumber(invoice.orderNumber).subscribe((order) => {
      this.detailedInvoice = order.invoice;
      this.customer = order.customer;
      this.address = order.customer.address.postalCode + ' ' + order.customer.address.street;
      this.promotionExists = order.promotion !== null;
      if (this.promotionExists) {
        this.promotion = order.promotion;
      }
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }
}
