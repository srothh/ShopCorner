import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../dtos/product';
import {Router, UrlSerializer} from '@angular/router';
import {forkJoin} from 'rxjs';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {Pagination} from '../../../dtos/pagination';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  @ViewChildren('checkboxes') checkboxes: QueryList<ElementRef>;
  products: Product[];
  categories: Category[];
  taxRates: TaxRate[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  selectedProducts: Product[] = [];
  errorOccurred: boolean;
  errorMessage: string;

  constructor(private productService: ProductService, private router: Router, private urlSerializer: UrlSerializer,
              private categoryService: CategoryService, private taxRateService: TaxRateService) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    forkJoin([this.productService.getProducts(this.page, this.pageSize),
      this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
      .subscribe(([productsData, categoriesData, taxRatesData]) => {
        this.products = productsData.items;
        this.collectionSize = productsData.totalItemCount;
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });

  }

  fetchProducts(): void {
    this.productService.getProducts(this.page, this.pageSize).subscribe((productData: Pagination<Product>) => {
      this.products = productData.items;
    });
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
    if (event.target.checked) {
      this.selectedProducts.push(this.products[index]);
    } else {
      const product = this.products[index];
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
          if ((this.page + 1) * this.pageSize >= this.collectionSize &&
            //products per page equals selected products -> return to previous page
            this.products.length === this.selectedProducts.length &&
            this.page > 0) {
            this.previousPage();
          } else {
            this.fetchProducts();
          }
          this.collectionSize -= this.selectedProducts.length;
          this.uncheckSelectedProducts();
        }
      }, error => {
        this.errorOccurred = true;
        this.errorMessage = error.error.message;
      });
    }
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page -= 1;
      this.fetchProducts();
    }
  }

  nextPage(): void {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.fetchProducts();
    }
  }
  resetState(){
    this.errorMessage = null;
    this.errorOccurred = undefined;
  }
}
