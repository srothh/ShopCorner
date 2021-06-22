import {Component, Input, OnInit} from '@angular/core';
import {Order} from '../../../dtos/order';

@Component({
  selector: 'app-base-shop-orders-header',
  templateUrl: './base-shop-orders-header.component.html',
  styleUrls: ['./base-shop-orders-header.component.scss']
})
export class BaseShopOrdersHeaderComponent implements OnInit {
  @Input()
  order: Order;
  constructor() { }

  ngOnInit(): void {
  }

}
