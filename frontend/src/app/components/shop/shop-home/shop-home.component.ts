import {Component, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './shop-home.component.html',
  styleUrls: ['./shop-home.component.scss']
})
export class ShopHomeComponent implements OnInit {

  products: Product[];
  page = 0;
  pageSize = 18;
  collectionSize = 0;

  error = false;
  errorMessage = '';

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService.getProducts(this.page, this.pageSize, undefined, 'saleCount').subscribe((productData) => {
        this.products = productData.items;
        this.collectionSize = productData.totalItemCount;
      }, error => {
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      });
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }
}
