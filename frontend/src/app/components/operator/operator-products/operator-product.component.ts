import { Component, OnInit } from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../dtos/product';
import {Router, UrlSerializer} from '@angular/router';
import {forkJoin} from 'rxjs';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  products: Product[];
  categories: Category[];
  taxRates: TaxRate[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  constructor(private productService: ProductService, private router: Router, private urlSerializer: UrlSerializer,
              private categoryService: CategoryService, private taxRateService: TaxRateService) { }
  ngOnInit(): void {
    this.fetchData();
  }
  fetchData(): void {
    forkJoin([this.productService.getProducts(this.page, this.pageSize),
      this.categoryService.getCategories(), this.taxRateService.getTaxRates(),
      this.productService.getNumberOfProducts()])
      .subscribe(([productsData, categoriesData,taxRatesData, numberOfProducts]) => {
        this.products = productsData;
        this.collectionSize = numberOfProducts;
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });

  }
  fetchProducts(): void{
    this.productService.getProducts(this.page, this.pageSize).subscribe((productData) => {
      this.products = productData;
    });
  }
  addNewProduct(): void {
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addProductURL = currentURL.replace('products','products/add');
    this.router.navigate([addProductURL],{state: [this.categories,this.taxRates]}).then();
  }
  goToProductDetails(selectedProduct: Product){
    const currentUri = this.urlSerializer.serialize(this.router.createUrlTree([]));
    this.router.navigate([currentUri + '/' + selectedProduct.id], {state: [this.categories, this.taxRates, selectedProduct]}).then();
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
}
