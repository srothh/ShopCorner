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
  constructor(private productService: ProductService, private router: Router, private urlSerializer: UrlSerializer,
              private categoryService: CategoryService, private taxRateService: TaxRateService) { }
  ngOnInit(): void {
    this.fetchData();
  }
  fetchData(): void {
    forkJoin([this.productService.getProducts(), this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
      .subscribe(([productsData, categoriesData,taxRatesData]) => {
        this.products = productsData;
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });

  }
  addNewProduct(): void {
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addProductURL = currentURL.replace('products','products/add');
    this.router.navigate([addProductURL],{state: [this.categories,this.taxRates]}).then();
  }
}
