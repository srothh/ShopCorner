import {Component, OnInit} from '@angular/core';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {FormBuilder, FormGroup, FormArray, Validators} from '@angular/forms';
import {InvoiceService} from '../../../services/invoice.service';

import {formatDate} from '@angular/common';
import {InvoiceItemKey} from '../../../dtos/invoiceItemKey';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {ProductService} from '../../../services/product.service';
import {UrlSerializer} from '@angular/router';
import {TaxRateService} from '../../../services/tax-rate.service';
import {forkJoin} from 'rxjs';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-operator-invoice',
  templateUrl: './operator-invoice.component.html',
  styleUrls: ['./operator-invoice.component.scss']
})
export class OperatorInvoiceComponent implements OnInit {

  shopName = 'shopCorner';

  newInvoiceForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';
  invoiceDto: Invoice;
  mapItem = {};
  map = [];
  products: Product[];

  subtotal = 0;
  total = 0;
  tax = 0;

  download = false;
  show = false;
  print = false;


  constructor(private invoiceService: InvoiceService, private productService: ProductService,
              private taxRateService: TaxRateService, private formBuilder: FormBuilder) {
  }


  ngOnInit() {
    this.newInvoiceForm = this.formBuilder.group({
      items: new FormArray([]),
      subtotal: [''],
      tax: [''],
      total: ['']
    });
    this.fetchData();
    this.addProductOnClick();
  }

  get f() {
    return this.newInvoiceForm.controls;
  }

  get t() {
    return this.f.items as FormArray;
  }


  addProductOnClick() {
    this.t.push(this.formBuilder.group({
      name: ['', Validators.required],
      price: [''],
      quantity: ['', [Validators.required]],
      amount: ['']
    }));
  }

  onSubmit() {
    this.submitted = true;
    if (this.newInvoiceForm.invalid) {
      return;
    }
    this.creatInvoiceDto();
    if (this.print) {
    } else if (this.show) {
      this.showInvoice();
    } else if (this.download) {
      this.downloadInvoice();
    }
    this.show = false;
    this.print = false;
    this.download = false;
    this.newInvoiceForm.reset();
    this.vanishError();
    this.submitted = false;
    this.onReset();
  }

  addInvoice() {
    this.invoiceService.createInvoice(this.invoiceDto);
  }

  creatInvoiceDto() {
    this.invoiceDto = new Invoice();
    for (const item of this.t.controls) {
      if (item !== undefined) {

        const numOfItems = item.value.quantity;
        const product = item.value.name;
        const invItem = new InvoiceItem(new InvoiceItemKey(product.id), product, numOfItems);

        this.invoiceDto.items.push(invItem);
        this.subtotal += product.price * item.value.quantity;
        this.tax += product.price * item.value.quantity * ((product.taxRate.percentage / 100));
        this.total = this.subtotal + this.tax;

        this.mapItem['product'] = item.value.name;
        this.mapItem['quantity'] = numOfItems;
        this.map.push(this.mapItem);
        this.mapItem = {};
      }
    }
    this.invoiceDto.amount = +this.total.toFixed(2);
    this.invoiceDto.date = formatDate(new Date(), 'yyyy-MM-ddTHH:mm:ss', 'en');
  }


  calculateAmount() {
    this.newInvoiceForm.value.total = this.calcTotal();
    this.newInvoiceForm.value.subtotal = this.calcSubtotal();
    this.newInvoiceForm.value.tax = this.calcTotalTax();
  }

  isNotaNumber(e){
    return isNaN(e);
  }

  onReset() {
    this.submitted = false;
    this.newInvoiceForm.reset();
    this.t.clear();
    this.addProductOnClick();
  }

  deleteProductFromInvoice(id: number) {
    if (this.t.length > 1) {
      this.t.removeAt(id);
    } else {
      this.errorHandling('Es können nicht alle Elemente einer Rechnung gelöscht werden');
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  downloadInvoice() {
    this.invoiceService.createInvoiceAsPdf(this.invoiceDto).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + this.invoiceDto.date + '.pdf';
      link.click();
    });
  }

  showInvoice() {
    this.invoiceService.createInvoiceAsPdf(this.invoiceDto).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const blobURL = URL.createObjectURL(newBlob);
      window.open(blobURL);
    });
  }


  downloadInvoiceById(id: number) {
    this.invoiceService.getInvoiceAsPdfById(-1).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + this.invoiceDto.date + '_' + id + '.pdf';
      link.click();
    });
  }

  showInvoiceById(id: number) {
    this.invoiceService.getInvoiceAsPdfById(-1).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const blobURL = URL.createObjectURL(newBlob);
      window.open(blobURL);
    });
  }


  /**
   * @param error
   * @private
   */
  private errorHandling(errorMessage: string) {
    if (errorMessage !== null) {
      this.error = true;
      this.errorMessage = errorMessage;
    } else {
      this.error = false;
    }
  }

  /**
   * @param error
   * @private
   */
  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }


  private fetchData(): void {
    forkJoin([this.productService.getProducts()])
      .subscribe(([productsData]) => {
        this.products = productsData;
      });
  }

  private calcTotal() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        if (product.taxRate !== undefined) {
          amount += product.price * item.value.quantity * ((product.taxRate.percentage / 100) + 1);
        }
      }
    }
    return amount.toFixed(2);
  }

  private calcTotalTax() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        if (product.taxRate !== undefined) {
          amount += product.price * item.value.quantity * ((product.taxRate.percentage / 100));
        }
      }
    }
    return amount.toFixed(2);
  }

  private calcSubtotal() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        amount += product.price * item.value.quantity;
      }
    }
    return amount.toFixed(2);
  }


}
