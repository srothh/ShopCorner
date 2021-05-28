import {Component, ElementRef, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {forkJoin} from 'rxjs';
import {Product} from '../../../dtos/product';


@Component({
  selector: 'app-operator-product-details',
  templateUrl: './operator-product-details.component.html',
  styleUrls: ['./operator-product-details.component.scss']
})
export class OperatorProductDetailsComponent implements OnInit {
  product: Product;
  categoryId: number;
  taxRateId: number;
  //properties for drop-down
  categories: Category[];
  taxRates: TaxRate[];
  // util properties
  shouldFetch: boolean;

  constructor(private categoryService: CategoryService, private taxRateService: TaxRateService, private router: Router) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.categories = this.router.getCurrentNavigation().extras.state[0] as Category[];
      this.taxRates = this.router.getCurrentNavigation().extras.state[1] as TaxRate[];
      this.product = this.router.getCurrentNavigation().extras.state[2] as Product;
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
      .subscribe(([categoriesData,taxRatesData]) => {
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });
  }
}
