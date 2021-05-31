import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../services/product/product.service';
import {Product} from '../../../dtos/product';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CategoryService} from '../../../services/category.service';
import {Category} from '../../../dtos/category';

@Component({
  selector: 'app-shop-product',
  templateUrl: './shop-product.component.html',
  styleUrls: ['./shop-product.component.scss']
})
export class ShopProductComponent implements OnInit {
  searchForm: FormGroup;

  products: Product[];
  categories: Category[];
  page = 0;
  pageSize = 15;
  totalPages = 1;
  collectionSize = 0;

  constructor(private productService: ProductService, private categoryService: CategoryService, private formBuilder: FormBuilder) {
    this.searchForm = this.formBuilder.group({
      searchText: [''],
      categoryId: [-1]
    });
  }

  ngOnInit(): void {
    this.fetchProducts();
    this.fetchCategories();
  }

  fetchProducts(): void {
    const name = this.searchForm.controls.searchText.value;
    const categoryId = this.searchForm.controls.categoryId.value;
    this.productService.getProducts(this.page, this.pageSize, name, undefined, categoryId).subscribe((productData) => {
      this.products = productData.items;
      this.totalPages = productData.totalPages;
      this.collectionSize = productData.totalItemCount;
      console.log(this.totalPages + '  ' + this.page);
    });
  }

  fetchCategories() {
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
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
}
