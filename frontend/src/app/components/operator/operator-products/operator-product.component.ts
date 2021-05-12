import { Component, OnInit } from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../dtos/product';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  products: Product[];
  constructor(private productService: ProductService) { }
  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getProducts().subscribe((res) => {
      this.products = res;
    });
  }
}
