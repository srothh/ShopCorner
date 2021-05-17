import {Component, OnInit} from '@angular/core';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {FormBuilder, FormGroup, FormArray, Validators, FormControl} from '@angular/forms';
import {InvoiceService} from '../../../services/invoice.service';

import {formatDate} from '@angular/common';

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

  constructor(private invoiceService: InvoiceService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.newInvoiceForm = this.formBuilder.group({
      items: new FormArray([])
    });
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
      price: ['', [Validators.required]],
      quantity: ['', [Validators.required]]
    }));
  }

  onSubmit() {
    this.submitted = true;
    if (this.newInvoiceForm.invalid) {
      return;
    }
    this.creatInvoiceDto();
     this.generatePdf();
    // this.addInvoice();
  }

  addInvoice() {
    this.invoiceService.createInvoice(this.invoiceDto).subscribe(
      (invoice: Invoice) => {
        console.log(invoice);
      },
      (error) => {
        this.defaultServiceErrorHandling(error);
      });
  }

  creatInvoiceDto() {
    this.invoiceDto = new Invoice();
    let amount = 0;
    for (const item of this.t.controls) {
      const product = new Product();
      const numOfItems = item.value.quantity;
      for (let j = 0; j < numOfItems ; j++) {
        product.name = item.value.name;
        product.price = item.value.price;
        this.invoiceDto.invoiceItems.push(product);
      }
      amount += item.value.price * item.value.quantity;
      this.mapItem['product'] = product;
      this.mapItem['quantity'] = numOfItems;
      this.map.push(this.mapItem);
      this.mapItem = {};
    }
    this.invoiceDto.amount = amount;
    this.invoiceDto.date = formatDate(new Date(), 'dd.MM.yyyy HH:mm:ss', 'en');
  }

   generatePdf() {
     const docDefinition = {
       content: [
         {
           text: `${this.shopName}`,
           fontSize: 20,
           fontStyle: 'Arial',
           alignment: 'center',
           color: 'black'
         },
         {
           text: 'Rechnung',
           fontSize: 16,
           bold: true,
           alignment: 'center',
           decoration: 'underline',
           color: 'black',
           margin: [0, 30, 0, 0 ]
         },
         {/*
           text: 'Customer Details',
           style: 'sectionHeader'*/
         },
         {
           columns: [
             [
               /*{
                 text: 'this.invoice.customerName',
                 bold: true
               },
               { text: 'this.invoice.address' },
               { text: 'this.invoice.email' },
               { text: 'this.invoice.contactNo' }*/
             ],
             [
               {
                 text: `Rechnungsdatum: ${this.invoiceDto.date}`,
                 alignment: 'right'
               },
               {
                 text: `Rechnungsnr : ${this.invoiceDto.id}`,
                 alignment: 'right'
               },
             ]
           ],
           margin: [0, 30 , 0, 0]
         },
         {
           text: 'Bestellungsdetails',
           style: 'sectionHeader',
           margin: [0, 90 , 0, 0]
         },
         {
           margin: [0, 15 , 0, 0],
           table: {
             margin: [0, 15 , 0, 0],
             headerRows: 1,
             headerFont: 'bold',
             widths: ['*', 'auto', 'auto', 'auto'],
             body: [
               [{ text: 'Produkt', bold: true }, { text: 'Preis', bold: true },
                 { text: 'Stück', bold: true }, { text: 'Gesamt', bold: true }]/*,
               [{text: 'Total Amount', colSpan: 3}, '' , '', '']*/
             ]
           }
         },
         {
           columns: [
             [{ qr: `https://www.tuwien.at/`, fit: '75' }],
           ],
           margin: [0, 15 , 0, 0]
         }/*,
         {
           text: 'Terms and Conditions',
           style: 'sectionHeader'
         },
         {
           ul: [
             'Order can be return in max 10 days.',
             'Warrenty of the product will be subject to the manufacturer terms and conditions.',
             'This is system generated invoice.',
           ],
         }*/
       ],
       footer: {
         style: 'sectionFooter',
         columns:
           [{text: `${this.shopName}` + '\n Gußhausstraße 10 1040 Wien\n e: office@tuwien.at\n t: +431 4000 84808',
             alignment: 'center', fontSize: 10}],
       },
       styles: {
         sectionHeader: {
           bold: true,
           decoration: 'underline',
           fontSize: 14,
           margin: [0, 15, 0, 15]
         },
         sectionFooter: {
           fontSize: 10,
           margin: [0, -30, 0, 30],
           height: 100
         }
       }
     };

     let total = 0;
     for (const item of this.map) {
       docDefinition['content'][5].table.body.push([item.product.name, item.product.price + '€',
         item.quantity, (total += (item.product.price * item.quantity)) + '€']);
     }
     const subTotalRow = JSON.parse(JSON.stringify([ {}, {}, {}, total.toString() + '€']));
     const taxRow = JSON.parse(JSON.stringify([ {}, {}, {}, total.toString() + '€']));
     const totalRow = JSON.parse(JSON.stringify([ {}, {}, {}, total.toString() + '€']));

     docDefinition['content'][5].table.body.push(subTotalRow);
     let index = docDefinition['content'][5].table.body.length;
     docDefinition['content'][5].table.body[index - 1][0] = JSON.parse(JSON.stringify({text: 'Zwischensumme.', colSpan: 3}));

     docDefinition['content'][5].table.body.push(taxRow);
     index = docDefinition['content'][5].table.body.length;
     docDefinition['content'][5].table.body[index - 1][0] = JSON.parse(JSON.stringify({text: 'Steuer.', colSpan: 3}));

     docDefinition['content'][5].table.body.push(totalRow);
     index = docDefinition['content'][5].table.body.length;
     docDefinition['content'][5].table.body[index - 1][0] = JSON.parse(JSON.stringify({text: 'Gesamtbetrag inklusive MwSt.', colSpan: 3}));

     console.log(total);
     pdfMake.createPdf(docDefinition).open();
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

}
