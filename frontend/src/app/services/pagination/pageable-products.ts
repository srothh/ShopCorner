import {IPageable} from './pageable.interface';
import {Product} from '../../dtos/product';
import {ProductService} from '../product/product.service';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PageableProducts implements IPageable<Product> {
  collectionSize = 0;
  page = 0;
  pageSize = 15;
  totalPages = 1;
  items: Product[];

  // Search query
  searchQuery = {
    name: '',
    categoryId: -1,
    sortBy: 'id',
  };

  error = false;
  errorMessage = '';

  constructor(private productService: ProductService) {
  }

  itemsIndexStart() {
    return 1 + (this.pageSize * (this.page));
  }

  itemsIndexEnd() {
    if (this.pageSize * (this.page + 1) < this.collectionSize) {
      return this.pageSize * (this.page + 1);
    }
    return this.collectionSize;
  }

  fetchProducts() {
    this.productService.getProducts(this.page, this.pageSize, this.searchQuery.name, this.searchQuery.sortBy, this.searchQuery.categoryId)
      .subscribe((productData) => {
        this.items = productData.items;
        this.totalPages = productData.totalPages;
        this.collectionSize = productData.totalItemCount;
      }, error => {
        this.error = true;
        this.errorMessage = error;
      });
  }

  /**
   * goes to next page if not on the last page
   */
  nextPage() {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.fetchProducts();
    }
  }

  /**
   * goes to previous page if not on the first page
   */
  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.fetchProducts();
    }
  }

  /**
   * Resets the page and fetches products
   */
  resetAndFetchProducts() {
    this.page = 0;
    this.fetchProducts();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }
}
