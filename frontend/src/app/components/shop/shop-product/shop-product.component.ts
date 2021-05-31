import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../services/product/product.service';
import {Product} from '../../../dtos/product';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-shop-product',
  templateUrl: './shop-product.component.html',
  styleUrls: ['./shop-product.component.scss']
})
export class ShopProductComponent implements OnInit {
  searchForm: FormGroup;

  products: Product[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;

  constructor(private productService: ProductService, private formBuilder: FormBuilder) {
    this.searchForm = this.formBuilder.group({
      searchText: [''],
    });
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    const name = this.searchForm.controls.searchText.value;
    this.productService.getProducts(this.page, this.pageSize, name).subscribe((productData) => {
      this.products = productData.items;
      this.collectionSize = productData.totalItemCount;
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

  searchProducts() {
    this.fetchProducts();
  }
}
