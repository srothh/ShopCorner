import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
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
  private productService: ProductService) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.categories = this.router.getCurrentNavigation().extras.state[0] as Category[];
      this.taxRates = this.router.getCurrentNavigation().extras.state[1] as TaxRate[];
      this.shouldFetch = false;
    } else {
      this.shouldFetch = true;
    }
  }

  ngOnInit(): void {
    if (this.newProduct === undefined) {
      this.newProduct = this.createDefaultProduct();
    }
    if (this.shouldFetch === true) {
      this.fetchData();
    }
  }
  createDefaultProduct(): Product {
    return new Product(null,'',null,null,null,null, null);
  }
  onSelectFile(event) {
    this.fileToUpload = event.target.files.item(0);
    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = ((loadEvent) => {
      //this.fileSource should be string in a base64-encoded format
      this.fileSource = loadEvent.target.result;
      if (typeof this.fileSource === 'string') {
        this.newProduct.picture = this.fileSource.split(',')[1];
      }
    });
  }
  addProduct(productForm: NgForm): void {
    if (!this.validateProduct(this.newProduct)){
      this.errorOccurred = true;
      return;
    }
     this.productService.addProduct(this.newProduct, this.newCategoryId, this.newTaxRateId).subscribe(data => {
       this.newProduct.id = data.id;
       this.errorOccurred = false;
     }, error => {
       this.errorOccurred = true;
     });
  }
  //client-side validation
  validateProduct(product: Product): boolean {
    console.log(product);
    if (product.name === null){
      this.errorMessage = 'Der Name muss mindestens 1 Zeichen besitzen';
      return false;
    }
    if (product.name.trim().length === 0){
      this.errorMessage = 'Der Name muss mindestens 1 Zeichen besitzen';
      return false;
    }
    product.name = product.name.trim();
    if (product.price === null){
      this.errorMessage = 'Bitte einen Preis in EUR definieren: z.B. 1.20';
      return false;
    }
    if (product.price != null){
      product.price = parseFloat(product.price.toFixed(2));
    }
    if (this.newTaxRateId == null){
      this.errorMessage = 'Bitte einen Steuersatz auswählen';
      return false;
    }
    return true;
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
  resetForm(form: NgForm){
    form.resetForm();
    this.errorOccurred = undefined;
  }
  clearImage(): void {
    this.fileToUpload = undefined;
    this.fileSource = undefined;
    this.fileInput.nativeElement.value = '';
  }

}
