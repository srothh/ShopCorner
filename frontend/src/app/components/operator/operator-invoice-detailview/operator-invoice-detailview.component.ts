import {Component, Input, OnInit} from '@angular/core';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';

@Component({
  selector: 'app-operator-invoice-detailview',
  templateUrl: './operator-invoice-detailview.component.html',
  styleUrls: ['./operator-invoice-detailview.component.scss']
})
export class OperatorInvoiceDetailviewComponent implements OnInit {
  @Input() value: Invoice;
  detailedInvocie: Invoice;
  error = false;
  errorMessage = '';
  download = false;

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.detailedInvocie = new Invoice();
    this.detailedInvocie.date = '';
    this.detailedInvocie.amount = 0;

    this.fetchData(this.value.id);
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

  private fetchData(id: number) {
    this.invoiceService.getInvoiceById(id).subscribe( (item) => {
      this.detailedInvocie = item;
      console.log(this.detailedInvocie);

    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }
}
