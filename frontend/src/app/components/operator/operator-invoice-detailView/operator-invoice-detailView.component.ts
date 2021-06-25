import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';
import {InvoiceType} from '../../../dtos/invoiceType.enum';
import {Customer} from '../../../dtos/customer';

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
  customerExists = false;
  address: string;
  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.detailedInvoice = new Invoice();
    this.detailedInvoice.date = '';
    this.detailedInvoice.amount = 0;
    this.customerExists = this.value.invoiceType === InvoiceType.customer ||
      (this.value.customerId !== undefined && this.value.customerId !== null);
    if (this.customerExists) {
      this.fetchCustomerData(this.value);
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
  private fetchCustomerData(invoice: Invoice) {
    this.invoiceService.getInvoiceById(invoice.id).subscribe( (item) => {
      this.detailedInvoice = item;

    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
      this.invoiceService.getCustomerById(invoice.customerId).subscribe((item) => {
        this.customer = item;
        this.address = item.address.postalCode + ' ' + item.address.street + ' ' + item.address.houseNumber;

      }, (error) => {
        this.error = true;
        this.errorMessage = error;
      });
  }
}
