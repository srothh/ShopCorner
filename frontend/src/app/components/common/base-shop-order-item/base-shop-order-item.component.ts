import {Component, Input, OnInit} from '@angular/core';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {Product} from '../../../dtos/product';

@Component({
  selector: 'app-base-shop-order-item',
  templateUrl: './base-shop-order-item.component.html',
  styleUrls: ['./base-shop-order-item.component.scss']
})
export class BaseShopOrderItemComponent implements OnInit {
  @Input()
  orderItems: InvoiceItem[];
  constructor() { }

  ngOnInit(): void {
  }
  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }

}
