import {Component, Input, OnInit} from '@angular/core';
import {IAuthService} from '../../../services/auth/interface-auth.service';
import {Product} from '../../../dtos/product';

@Component({
  selector: 'app-base-shop-product-item',
  templateUrl: './base-shop-product-item.component.html',
  styleUrls: ['./base-shop-product-item.component.scss']
})
export class BaseShopProductItemComponent implements OnInit {

  @Input() public product: Product;

  constructor() { }

  ngOnInit(): void {
  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return '../../../../assets/stock-productimage-unavailable.jpg';
  }
}
