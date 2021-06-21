import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../../services/order.service';
import {Order} from '../../../dtos/order';
import {Pagination} from '../../../dtos/pagination';
import {MeService} from '../../../services/me.service';
import {Customer} from '../../../dtos/customer';

@Component({
  selector: 'app-account-orders',
  templateUrl: './shop-account-orders.component.html',
  styleUrls: ['./shop-account-orders.component.scss']
})
export class ShopAccountOrdersComponent implements OnInit {
  error = false;
  errorMessage = '';
  orders: Order[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  customer: Customer;

  constructor(private orderService: OrderService,
              private meService: MeService) { }

  ngOnInit(): void {
    this.loadCustomer();
    this.loadOrdersForPage();
  }
  loadOrdersForPage() {
    this.orderService.getOrdersForPage(this.customer.id).subscribe(
      (paginationDto: Pagination<Order>) => {
        console.log(paginationDto);
        this.orders = paginationDto.items;
        this.collectionSize = paginationDto.totalItemCount;
        console.log('myOrders are: ', this.orders);
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
  loadCustomer(){
    this.meService.getMyProfileData().subscribe((customerData)=>{
      this.customer = customerData;
    });
  }

}
