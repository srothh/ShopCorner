import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../../dtos/product';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-base-shop-product-item',
  templateUrl: './base-shop-product-item.component.html',
  styleUrls: ['./base-shop-product-item.component.scss']
})
export class BaseShopProductItemComponent implements OnInit {

  @Input() public product: Product;

  constructor(private globals: Globals) { }

  ngOnInit(): void {
  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }
}
