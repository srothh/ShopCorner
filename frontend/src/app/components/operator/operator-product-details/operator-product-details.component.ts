import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {forkJoin} from 'rxjs';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product/product.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';


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
  errorOccurred: boolean;
  errorMessage: string;

  constructor(
    private categoryService: CategoryService,
    private taxRateService: TaxRateService,
    private productService: ProductService,
    private router: Router,
    private authService: OperatorAuthService) {
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

  /**
   * calls on authentication service to return permission of logged in operator
   *
   * @return string role of logged in operator
   */
  getPermission(): string {
    return this.authService.getUserRole();
  }

  fetchData(): void {
    forkJoin([this.categoryService.getCategories(), this.taxRateService.getTaxRates()])
      .subscribe(([categoriesData,taxRatesData]) => {
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });
  }

  deleteProduct(){
    this.productService.deleteProduct(this.product.id).subscribe(()=>{
      this.router.navigate(['/operator/products']).then();
    }, error => {
      this.errorOccurred = true;
      this.errorMessage = error.error.message;
    });
  }

  resetState(){
    this.errorMessage = null;
    this.errorOccurred = undefined;
  }

  goBackToProductsOverview(){
    this.router.navigate(['/operator/products']).then();
  }
}
