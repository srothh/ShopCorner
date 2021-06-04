import {Component, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product/product.service';
import {Router} from '@angular/router';

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

  constructor(private productService: ProductService, private router: Router) {
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

  goToProductDetails(id: number, selectedIndex: number) {
    const product = this.products[selectedIndex];
    this.router.navigate(['products/' + id], {state: {product}}).then();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }
}
