import {Component, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product.service';

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

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService.getProductsSortedBySales(this.page, this.pageSize).subscribe((productData) => {
      this.products = productData;
      this.getCollectionSize();
    });
  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return '../../../../assets/stock-productimage-unavailable.jpg';
  }

  getCollectionSize() {
    this.productService.getNumberOfProducts().subscribe((count: number) => this.collectionSize = count);
  }

}
