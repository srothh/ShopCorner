import {Component, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {InvoiceService} from '../../../services/invoice.service';

import {formatDate} from '@angular/common';
import {InvoiceItemKey} from '../../../dtos/invoiceItemKey';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {InvoiceType} from '../../../dtos/invoiceType.enum';


@Component({
  selector: 'app-operator-invoice-form',
  templateUrl: './operator-invoice-form.component.html',
  styleUrls: ['./operator-invoice-form.component.scss']
})
export class OperatorInvoiceFormComponent implements OnInit {

  shopName = 'shopCorner';

  newInvoiceForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';
  invoiceDto: Invoice;
  mapItem = {};
  map = [];
  products: Product[];
  selectedProducts: Product[];
  subtotal = 0;
  total = 0;
  tax = 0;
  download = false;
  show = false;

  constructor(private invoiceService: InvoiceService, private formBuilder: FormBuilder) {
  }
  static validateInputNumbers(item) {
    return (item.taxRate !== undefined && item.quantity !== undefined) || (item.taxRate !== '' && item.quantity !== '');
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
    this.selectedProducts = [];
  }

  get invoiceFrom() {
    return this.newInvoiceForm.controls;
  }

  get invoiceFormArray() {
    return this.invoiceFrom.items as FormArray;
  }

  formControls(item) {
    return item.controls;
  }

  formControlsQuantity(item) {
    return this.formControls(item).quantity;
  }

  formControlsName(item) {
    return this.formControls(item).name;
  }

  addProductOnClick() {
    this.invoiceFormArray.push(this.formBuilder.group({
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
    if (this.show) {
      this.showInvoice();
    } else if (this.download) {
      this.downloadInvoice();
    }
    this.show = false;
    this.download = false;
    this.newInvoiceForm.reset();
    this.vanishError();
    this.submitted = false;
    this.onReset();
    this.updateProducts();
  }

  addInvoice() {
    this.invoiceService.createInvoice(this.invoiceDto);
  }

  creatInvoiceDto() {
    this.invoiceDto = new Invoice();
    this.invoiceDto.invoiceNumber = '';
    for (const item of this.invoiceFormArray.controls) {
      if (item !== undefined) {

        const numOfItems = item.value.quantity;
        const product = item.value.name;
        const invItem = new InvoiceItem(new InvoiceItemKey(product.id), product, numOfItems);

        this.invoiceDto.items.push(invItem);
        this.subtotal += product.price * item.value.quantity;
        this.tax += product.price * item.value.quantity * ((product.taxRate.calculationFactor - 1));
        this.total = this.subtotal + this.tax;

        this.mapItem['product'] = item.value.name;
        this.mapItem['quantity'] = numOfItems;
        this.map.push(this.mapItem);
        this.mapItem = {};
      }
    }
    this.invoiceDto.amount = +this.total.toFixed(2);
    this.invoiceDto.date = formatDate(new Date(), 'yyyy-MM-ddTHH:mm:ss', 'en');
    this.invoiceDto.invoiceType = InvoiceType.operator;
    console.log(this.invoiceDto);
  }


  calculateAmount() {
    this.newInvoiceForm.value.total = this.calcTotal();
    this.newInvoiceForm.value.subtotal = this.calcSubtotal();
    this.newInvoiceForm.value.tax = this.calcTotalTax();
  }

  isNotANumber(e) {
    return isNaN(e);
  }

  onReset() {
    this.submitted = false;
    this.newInvoiceForm.reset();
    this.invoiceFormArray.clear();
    this.addProductOnClick();
    this.updateProducts();
  }

  updateProducts() {
    this.selectedProducts = [];
    for (const item of this.invoiceFormArray.controls) {
      const index = this.products.indexOf(item.value.name);
      this.selectedProducts.push(this.products[index]);
    }
  }

  deleteProductFromInvoice(id: number) {
    if (this.invoiceFormArray.length > 1) {
      this.invoiceFormArray.removeAt(id);
    } else {
      this.errorHandling('Es können nicht alle Elemente einer Rechnung gelöscht werden');
    }
    this.updateProducts();
    this.calculateAmount();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  downloadInvoice() {
    this.invoiceService.createInvoiceAsPdf(this.invoiceDto).subscribe((data) => {
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + this.invoiceDto.date + '.pdf';
      link.click();
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  showInvoice() {
    this.invoiceService.createInvoiceAsPdf(this.invoiceDto).subscribe((data) => {
      const newBlob  = new Blob([data], {type: 'application/pdf'});
      const blobURL = URL.createObjectURL(newBlob);
      window.open(blobURL);
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  /**
   * @param errorMessage
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



  private fetchData(): void {
    this.invoiceService.getProducts().subscribe((productsData) => {
        this.products = productsData;
      }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  private calcTotal() {
    let amount = 0.00;
    for (const item of this.invoiceFormArray.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        if (product.taxRate !== undefined && OperatorInvoiceFormComponent.validateInputNumbers(item.value)) {
          amount += product.price * item.value.quantity * product.taxRate.calculationFactor;
        }
      }
    }
    return amount.toFixed(2);
  }

  private calcTotalTax() {
    let amount = 0.00;
    for (const item of this.invoiceFormArray.controls) {
      if (item !== undefined) {
        const product = item.value.name;
        if (product.taxRate !== undefined && OperatorInvoiceFormComponent.validateInputNumbers(item.value)) {
          amount += product.price * item.value.quantity * ((product.taxRate.calculationFactor - 1));
        }
      }
    }
    return amount.toFixed(2);

  }

  private calcSubtotal() {
    let amount = 0;
    for (const item of this.invoiceFormArray.controls) {
      if (item !== undefined && item.value !== undefined) {
        const product = item.value.name;
        if (product.taxRate !== undefined && OperatorInvoiceFormComponent.validateInputNumbers(item.value)) {
          amount += product.price * item.value.quantity;
        }
      }
    }
    return amount.toFixed(2);

  }



}
