import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {OrderService} from '../../../services/order.service';
import {Order} from '../../../dtos/order';
import {InvoiceItem} from '../../../dtos/invoiceItem';

@Component({
  selector: 'app-shop-account-order-details',
  templateUrl: './shop-account-order-details.component.html',
  styleUrls: ['./shop-account-order-details.component.scss']
})
export class ShopAccountOrderDetailsComponent implements OnInit {

  error = false;
  errorMessage = '';

  order: Order;
  items: InvoiceItem[];
  date: Date;

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    this.orderService.getOrderById(id)
      .subscribe(order => this.order = order, error => {
        console.log(error);
        this.error = true;
        if (typeof error.error === 'object') {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = error.error;
        }
      }, ()=>{
          this.items = this.order.invoice.items;
          this.date = new Date(this.order.invoice.date);
    });
  }

  vanishError() {
    this.error = false;
  }

}
