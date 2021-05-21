import {Component, OnInit} from '@angular/core';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {FormBuilder, FormGroup, FormArray, Validators, FormControl} from '@angular/forms';
import {InvoiceService} from '../../../services/invoice.service';

import {formatDate} from '@angular/common';
import {InvoiceItemKey} from '../../../dtos/invoiceItemKey';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {ProductService} from '../../../services/product.service';
import {Router, UrlSerializer} from '@angular/router';
import {CategoryService} from '../../../services/category.service';
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


  constructor(private invoiceService: InvoiceService, private productService: ProductService,
              private taxRateService: TaxRateService, private formBuilder: FormBuilder, private urlSerializer: UrlSerializer) {
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
     // this.generatePdf();
    this.addInvoice();
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
    console.log(this.invoiceDto);
  }

  calculateAmount() {

    this.newInvoiceForm.value.total = this.calcTotal();
    this.newInvoiceForm.value.subtotal = this.calcSubtotal();
    this.newInvoiceForm.value.tax = this.calcTotalTax();

    console.log(this.newInvoiceForm.value);


  }

  calcTotal() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        amount += product.price * item.value.quantity * ((product.taxRate.percentage / 100) + 1);
      }
    }
    return amount.toFixed(2);
  }

  calcTotalTax() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        amount += product.price * item.value.quantity * ((product.taxRate.percentage / 100));
      }
    }
    return amount.toFixed(2);
  }

  calcSubtotal() {
    let amount = 0;
    for (const item of this.t.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        amount += product.price * item.value.quantity;
      }
    }
    return amount.toFixed(2);
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


}
