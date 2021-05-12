import { Component, OnInit } from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../dtos/product';
import {Router, UrlSerializer} from '@angular/router';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  products: Product[];
  constructor(private productService: ProductService, private router: Router, private urlSerializer: UrlSerializer) { }
  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(): void {
    this.productService.getProducts().subscribe((data) => {
      this.products = data;
    });
  }
  addNewProduct(): void {
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addProductURL = currentURL.replace('products','add-product');
    this.router.navigate([addProductURL]).then();
  }
}
