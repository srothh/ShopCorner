import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ProductService} from '../../../services/product/product.service';
import {Product} from '../../../dtos/product';
import {Router, UrlSerializer} from '@angular/router';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {faFilter} from '@fortawesome/free-solid-svg-icons';
import {PageableProducts} from '../../../services/pagination/pageable-products';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  @ViewChildren('checkboxes') checkboxes: QueryList<ElementRef>;
  faFilter = faFilter;
  categories: Category[];
  taxRates: TaxRate[];
  selectedProducts: Product[] = [];
  error: boolean;
  errorMessage: string;

  constructor(private productService: ProductService,
              private router: Router,
              private urlSerializer: UrlSerializer,
              private categoryService: CategoryService,
              private taxRateService: TaxRateService,
              private authService: OperatorAuthService,
              public pageableProducts: PageableProducts) {
  }

  ngOnInit(): void {
    this.fetchProducts();
    this.fetchCategoriesAndTaxRates();
  }

  /**
   * calls on authentication service to return permission of logged in operator
   *
   * @return string role of logged in operator
   */
  getPermission(): string {
    return this.authService.getUserRole();
  }

  fetchProducts(): void {
    this.pageableProducts.fetchProducts();
  }

  fetchCategoriesAndTaxRates() {
    forkJoin([this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
      .subscribe(([categoriesData, taxRatesData]) => {
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      }, (error) => {
        this.error = true;
        this.error = error;
      });
  }


  resetAndFetchProducts(searchForm) {
    this.pageableProducts.searchQuery = {
      name: searchForm.controls.searchText.value,
      categoryId: searchForm.controls.categoryId.value,
      sortBy: 'id',
    };
    this.pageableProducts.resetAndFetchProducts();
  }

  getPaginatedProducts() {
    return this.pageableProducts.items;
  }

  addNewProduct(): void {
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addProductURL = currentURL.replace('products', 'products/add');
    this.router.navigate([addProductURL], {state: [this.categories, this.taxRates]}).then();
  }

  goToProductDetails(selectedProduct: Product, event) {
    const targetHTMLElement = event.target.toString();
    // if a checkbox was clicked then stop cell-click-event routing to details page
    // checkbox is an input type with an corresponding label type -> therefore only if the target HTML Element
    // is either input or label -> then do not route to details page
    if (!(targetHTMLElement.includes('HTMLLabelElement') || targetHTMLElement.includes('HTMLInputLabel'))) {
      const currentUri = this.urlSerializer.serialize(this.router.createUrlTree([]));
      this.router.navigate([currentUri + '/' + selectedProduct.id], {state: [this.categories, this.taxRates, selectedProduct]}).then();
    }
  }

  clickedCheckMark(event, index: number) {
    // no propagation to details site allowed when clicking the checkbox
    event.stopPropagation();
    const product = this.getPaginatedProducts()[index];
    if (event.target.checked) {
      this.selectedProducts.push(product);
    } else {
      const deleteIndex = this.selectedProducts.indexOf(product);
      this.selectedProducts.splice(deleteIndex, 1);
    }
  }

  uncheckSelectedProducts() {
    this.checkboxes.forEach((element) => {
      element.nativeElement.checked = false;
    });
    this.selectedProducts = [];
  }

  deleteProducts() {
    for (const selectedProduct of this.selectedProducts) {
      this.productService.deleteProduct(selectedProduct.id).subscribe(() => {
        if (this.selectedProducts.indexOf(selectedProduct) === this.selectedProducts.length - 1) {
          if ((this.pageableProducts.page + 1) * this.pageableProducts.pageSize >= this.pageableProducts.collectionSize &&
            // products per page equals selected products -> return to previous page
            this.getPaginatedProducts().length === this.selectedProducts.length &&
            this.pageableProducts.page > 0) {
            this.pageableProducts.previousPage();
          } else {
            this.fetchProducts();
          }
          this.pageableProducts.collectionSize -= this.selectedProducts.length;
          this.uncheckSelectedProducts();
        }
      }, error => {
        this.error = true;
        this.errorMessage = error;
      });
    }
  }

  errorOccurred() {
    return this.error || this.pageableProducts.error;
  }

  getErrorMessage() {
    return this.pageableProducts.errorMessage;
  }

  resetState() {
    this.errorMessage = null;
    this.error = undefined;
    this.pageableProducts.vanishError();
  }
}
