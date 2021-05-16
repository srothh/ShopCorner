import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {Product} from '../../../dtos/product';
import {Router} from '@angular/router';
import {TaxRate} from '../../../dtos/tax-rate';
import {Category} from '../../../dtos/category';
import {forkJoin} from 'rxjs';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {ProductService} from '../../../services/product.service';

@Component({
  selector: 'app-operator-add-product',
  templateUrl: './operator-add-product.component.html',
  styleUrls: ['./operator-add-product.component.scss']
})
export class OperatorAddProductComponent implements OnInit {
  //properties for the image
  @ViewChild('fileInput')
  fileInput: ElementRef;
  fileToUpload: File;
  fileSource: string | ArrayBuffer;
  //Form for creating a new product
  productForm: FormGroup;
  //properties for the newly to be added product
  newProduct: Product;
  newCategoryId: number;
  newTaxRateId: number;
  //properties for drop-down
  categories: Category[];
  taxRates: TaxRate[];
  // util properties
  shouldFetch: boolean;
  errorOccurred: boolean;
  errorMessage: string;

  constructor(private router: Router, private categoryService: CategoryService, private taxRateService: TaxRateService,
  private productService: ProductService, private formBuilder: FormBuilder) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.categories = this.router.getCurrentNavigation().extras.state[0] as Category[];
      this.taxRates = this.router.getCurrentNavigation().extras.state[1] as TaxRate[];
      this.shouldFetch = false;
    } else {
      this.shouldFetch = true;
    }
  }

  ngOnInit(): void {
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]],
      description: [''],
      price: ['', Validators.required],
      taxRate: [ null , Validators.required],
      category: [null ]

    });
    if (this.newProduct === undefined){
      this.newProduct = this.createNewProduct();
    }
    if (this.shouldFetch === true) {
      this.fetchData();
    }
  }
  createNewProduct(): Product {
    return new Product(null,'',null,null,null,null, null);
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
  addProduct(): void {
    if (this.productForm.invalid){
      return;
    }
    this.newProduct.name = this.productForm.get('name').value;
    this.newProduct.price = this.productForm.get('price').value;
    this.newTaxRateId = this.productForm.get('taxRate').value;
    this.newProduct.description = this.productForm.get('description').value;
    this.newCategoryId = this.productForm.get('category').value;

    this.productService.addProduct(this.newProduct, this.newCategoryId, this.newTaxRateId).subscribe(data => {
       this.newProduct.id = data.id;
       this.errorOccurred = false;
     }, error => {
       this.errorOccurred = true;
     });
  }
  fetchData(): void {
      forkJoin([this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
        .subscribe(([categoriesData,taxRatesData]) => {
          this.categories = categoriesData;
          this.taxRates = taxRatesData;
        });
  }
  resetState(): void {
    this.errorOccurred = undefined;
    this.errorMessage = '';
  }
  resetForm(){
    this.productForm.reset();
    this.errorOccurred = undefined;
    this.fileToUpload = undefined;
    this.fileSource = undefined;
  }
  clearImage(): void {
    this.fileToUpload = undefined;
    this.fileSource = undefined;
    this.fileInput.nativeElement.value = '';
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
