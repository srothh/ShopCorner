import {Component, ElementRef, Input,  OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Product} from '../../../dtos/product';
import {Router} from '@angular/router';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {ProductService} from '../../../services/product.service';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-operator-product-form',
  templateUrl: './operator-product-form.component.html',
  styleUrls: ['./operator-product-form.component.scss']
})
export class OperatorProductFormComponent implements OnInit {
  @Input()
  newProduct: Product;
  @Input()
  newCategoryId: number;
  @Input()
  newTaxRateId: number;
  //properties for drop-down
  @Input()
  categories: Category[];
  @Input()
  taxRates: TaxRate[];
  //properties for the image
  @ViewChild('fileInput')
  fileInput: ElementRef;
  fileToUpload: File;
  fileSource: string | ArrayBuffer;
  //Form for creating a new product
  productForm: FormGroup;
  //properties for the newly to be added product

  //util properties
  shouldFetch: boolean;
  errorOccurred: boolean;
  errorMessage: string;
  inAddProduct: boolean;
  constructor(
    private router: Router,
    private productService: ProductService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    if (this.newProduct === undefined && this.router.url.includes('add')){
      this.newProduct = this.createNewProduct();
      this.inAddProduct = true;
    } else {
      this.inAddProduct = false;
    }
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]],
      description: [null,Validators.maxLength(70)],
      price: ['', Validators.required],
      taxRate: [ null , Validators.required],
      category: [null ]
    });
    if (this.inAddProduct === false){
      this.productForm.disable();
      this.productForm.controls['name'].setValue(this.newProduct.name);
      this.productForm.controls['description'].setValue(this.newProduct.description);
      this.productForm.controls['price'].setValue(this.newProduct?.price);
      this.productForm.controls['taxRate'].setValue(this.newProduct.taxRate.id, {onlySelf: true});
      this.productForm.controls['category'].setValue(this.newProduct.category?.id, {onlySelf: true});
      this.fileSource = 'data:image/png;base64,'+ this.newProduct.picture;
    }
  }
  createNewProduct(): Product {
    return new Product(null,'',null,null,null,null, null);
  }
  addProduct(): void {
    if (this.productForm.invalid){
      return;
    }
    this.newProduct.name = this.productForm.get('name').value;
    this.newProduct.name = this.newProduct.name.trim();
    this.newProduct.price = this.productForm.get('price').value;
    this.newTaxRateId = this.productForm.get('taxRate').value;
    if (this.productForm.get('description').value != null) {
      this.newProduct.description = this.productForm.get('description').value;
      this.newProduct.description = this.newProduct.description.trim();
    }

    this.newCategoryId = this.productForm.get('category').value;

    this.productService.addProduct(this.newProduct, this.newCategoryId, this.newTaxRateId).subscribe(data => {
      this.newProduct.id = data.id;
      this.errorOccurred = false;
    }, error => {
      this.errorOccurred = true;
      //NOTE: not all error types supported yet because of the way how the interceptor is handling errors
      this.errorMessage = error;
    });
  }
  resetState(): void {
    this.errorOccurred = undefined;
    this.errorMessage = undefined;
  }
  resetForm(){
    this.productForm.reset();
    this.errorOccurred = undefined;
    this.fileToUpload = undefined;
    this.fileSource = undefined;
  }
  onSelectFile(event) {
    this.fileToUpload = event.target.files.item(0);
    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = ((loadEvent) => {
      //fileSource should be string in a base64-encoded format
      this.fileSource = loadEvent.target.result;
      if (typeof this.fileSource === 'string') {
        this.newProduct.picture = this.fileSource.split(',')[1];
      }
    });
  }
  clearImage(): void {
    this.fileToUpload = undefined;
    this.fileSource = undefined;
    this.fileInput.nativeElement.value = '';
    this.newProduct.picture = null;
  }
  changeCategory(event){
    this.productForm.get('category').setValue(event.target.value, {
      onlySelf: true
    });
  }
  changeTaxRate(event){
    this.productForm.get('taxRate').setValue(event.target.value, {
      onlySelf: true
    });
  }
  get productFormControl() {
    return this.productForm.controls;
  }
  whiteSpaceValidator(control: AbstractControl) {
    const isWhitespace = (control.value || '').trim().length < 3;
    const isValid = !isWhitespace;
    return isValid ? null : { whitespace: true };
  }

}
