import {Component, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {Router} from '@angular/router';
import {TaxRate} from '../../../dtos/tax-rate';
import {Category} from '../../../dtos/category';
import {forkJoin} from 'rxjs';
import {CategoryService} from '../../../services/category/category.service';
import {TaxRateService} from '../../../services/tax-rate/tax-rate.service';

@Component({
  selector: 'app-operator-add-product',
  templateUrl: './operator-add-product.component.html',
  styleUrls: ['./operator-add-product.component.scss']
})
export class OperatorAddProductComponent implements OnInit {
  // properties for the newly to be added product
  product: Product;
  categoryId: number;
  taxRateId: number;
  // properties for drop-down
  categories: Category[];
  taxRates: TaxRate[];
  // util properties
  shouldFetch: boolean;
  errorOccurred: boolean;
  errorMessage: string;

  constructor(private router: Router, private categoryService: CategoryService, private taxRateService: TaxRateService) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.categories = this.router.getCurrentNavigation().extras.state[0] as Category[];
      this.taxRates = this.router.getCurrentNavigation().extras.state[1] as TaxRate[];
      this.shouldFetch = false;
    } else {
      this.shouldFetch = true;
    }
  }

  ngOnInit(): void {
    if (this.shouldFetch === true) {
      this.fetchData();
    }
  }

  fetchData(): void {
    forkJoin([this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
      .subscribe(([categoriesData, taxRatesData]) => {
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      }, (error) => {
        this.errorMessage = error;
        this.errorOccurred = true;
      });
  }

  vanishError() {
    this.errorOccurred = false;
  }
}
