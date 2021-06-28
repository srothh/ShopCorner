import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../../../services/category/category.service';
import {Category} from '../../../dtos/category';
import {ProductService} from '../../../services/product/product.service';
import {faFilter, faSearch} from '@fortawesome/free-solid-svg-icons';
import {Router} from '@angular/router';
import {PageableProducts} from '../../../services/pagination/pageable-products';

@Component({
  selector: 'app-shop-product',
  templateUrl: './shop-product.component.html',
  styleUrls: ['./shop-product.component.scss']
})
export class ShopProductComponent implements OnInit {
  categories: Category[];

  faFilter = faFilter;
  faSearch = faSearch;

  error = false;
  errorMessage = '';

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private router: Router,
              public pageableProducts: PageableProducts) {
    this.pageableProducts.pageSize = 18;
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.pageableProducts.fetchProducts();
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

  goToProductDetails(id: number, selectedIndex: number) {
    const currentUri = this.router.url;
    const product = this.getPaginatedProducts()[selectedIndex];
    this.router.navigate([currentUri + '/' + id], {state: {product}}).then();
  }

  errorOccurred() {
    return this.pageableProducts.error;
  }

  getErrorMessage() {
    return this.pageableProducts.errorMessage;
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.pageableProducts.vanishError();
  }
}
