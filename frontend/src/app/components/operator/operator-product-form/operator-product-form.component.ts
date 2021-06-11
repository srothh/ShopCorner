import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Product} from '../../../dtos/product';
import {ActivatedRoute, Router} from '@angular/router';
import {ProductService} from '../../../services/product/product.service';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

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
  addProductEnabled: boolean;
  inEditMode: boolean;

  constructor(
    private router: Router,
    private productService: ProductService,
    private formBuilder: FormBuilder,
    private activatedRouter: ActivatedRoute,
    private authService: OperatorAuthService
  ) {
  }

  ngOnInit(): void {
    if (this.newProduct === undefined && this.router.url.includes('add')) {
      this.newProduct = this.createNewProduct();
      //keeping track of which type of form we are currently in
      // in this case we are currently trying to save a brand new product entity
      this.addProductEnabled = true;
      this.inEditMode = false;
    } else {
      //in this case we are trying to edit a existing product entity
      this.addProductEnabled = false;
      this.inEditMode = true;
    }
    //build form
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]],
      description: [null, Validators.maxLength(70)],
      price: ['', Validators.required],
      taxRate: [null, Validators.required],
      category: [null],
      locked: [false]
    });
    // if the form is a 'edit-product-form' then set all properties in the form and make them readonly
    if (this.addProductEnabled === false) {
      this.setFormProperties();
      this.productForm.disable();
    }
  }

  /**
   * calls on authentication service to return permission of logged in operator
   *
   * @return string role of logged in operator
   */
  getPermission(): string {
    return this.authService.getUserRole();
  }

  createNewProduct(): Product {
    return new Product(null,
      '',
      null,
      null,
      new Category(null, null),
      new TaxRate(null, null, null),
      false,
      null,
      false);
  }
  setFormProperties(): void {
    if (this.newProduct === undefined) {
      const productId = +this.activatedRouter.snapshot.paramMap.get('id');
      this.fetchSelectedProduct(productId);
    } else {
      this.productForm.controls['name'].setValue(this.newProduct.name);
      this.productForm.controls['description'].setValue(this.newProduct.description);
      this.productForm.controls['price'].setValue(this.newProduct?.price);
      this.productForm.controls['locked'].setValue(this.newProduct.locked);
      this.productForm.controls['taxRate'].setValue(this.newProduct.taxRate.id, {onlySelf: true});
      this.productForm.controls['category'].setValue(this.newProduct.category?.id, {onlySelf: true});
      if (this.newProduct.category == null){
        this.newProduct.category = new Category(null, null);
      }
      if (this.newProduct.picture != null) {
        this.fileSource = 'data:image/png;base64,' + this.newProduct.picture;
      }
    }
  }

  addProduct(): void {
    if (this.productForm.invalid) {
      return;
    }
    this.newProduct.name = this.productForm.get('name').value.trim();
    this.newProduct.price = this.productForm.get('price').value;
    this.newProduct.taxRate.id = this.productForm.get('taxRate')?.value;
    if (this.productForm.get('description').value != null) {
      this.newProduct.description = this.productForm.get('description').value;
      this.newProduct.description = this.newProduct.description.trim();
    }
    if (this.productForm.get('category').value != null) {
      this.newProduct.category.id = this.productForm.get('category')?.value;
    }
    this.newProduct.locked = this.productForm.get('locked').value;
    this.newProduct.deleted = false;
    console.log(this.newProduct);
    if (this.router.url.includes('add')) {
      this.productService.addProduct(this.newProduct).subscribe(data => {
        this.newProduct.id = data.id;
        this.errorOccurred = false;
        this.router.navigate(['operator/products']).then();
      }, error => {
        this.errorOccurred = true;
        //NOTE: not all error types supported yet because of the way how the interceptor is handling errors
        this.errorMessage = error.error.message;
      });
    } else {
      this.updateProduct();
    }
  }

  updateProduct() {
    this.productService.updateProduct(this.newProduct.id,this.newProduct).subscribe(() => {
        this.inEditMode = true;
        this.addProductEnabled = false;
        this.productForm.disable();

      }, error => {
        this.errorOccurred = true;
        this.errorMessage = error.error.message;
      }
    );
  }

  fetchSelectedProduct(id: number) {
    this.productService.getProductById(id).subscribe(productData => {
      this.newProduct = productData;
      this.setFormProperties();
    });
  }

  enableEditing(): void {
    this.productForm.enable();
    //it is possible to edit the product and eventually make it 'save-able'
    this.addProductEnabled = true;
  }

  resetState(): void {
    this.errorOccurred = undefined;
    this.errorMessage = undefined;
  }

  resetForm() {
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

  changeCategory(event) {
    this.productForm.get('category').setValue(event.target.value, {
      onlySelf: true
    });
  }

  changeTaxRate(event) {
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
    return isValid ? null : {whitespace: true};
  }

}
