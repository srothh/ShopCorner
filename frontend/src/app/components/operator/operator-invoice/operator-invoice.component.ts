import {Component, OnInit} from '@angular/core';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {FormBuilder, FormGroup, FormArray, Validators, FormControl} from '@angular/forms';
import {InvoiceService} from '../../../services/invoice.service';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-operator-invoice',
  templateUrl: './operator-invoice.component.html',
  styleUrls: ['./operator-invoice.component.scss']
})
export class OperatorInvoiceComponent implements OnInit {
  newInvoiceForm: FormGroup;
  submitted = false;
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.newInvoiceForm = this.formBuilder.group({
      items: new FormArray([])
    });
    this.addProductOnClick();
    }

  // convenience getters for easy access to form fields
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

    // stop here if form is invalid
    if (this.newInvoiceForm.invalid) {
      return;
    }

    // display form values on success
    alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.newInvoiceForm.value, null, 4));
  }

  onReset() {
    this.submitted = false;
    this.newInvoiceForm.reset();
    this.t.clear();
    this.addProductOnClick();

  }

  deleteProduct(id: number) {
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
}
