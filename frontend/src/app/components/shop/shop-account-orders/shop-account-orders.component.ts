import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../../services/order.service';
import {Order} from '../../../dtos/order';
import {Pagination} from '../../../dtos/pagination';
import {MeService} from '../../../services/me.service';
import {Customer} from '../../../dtos/customer';
import {Product} from '../../../dtos/product';

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
  pageSize = 5;
  collectionSize = 0;
  customer: Customer;

  constructor(private orderService: OrderService,
              private meService: MeService) { }

  ngOnInit(): void {
    this.loadCustomer();
  }
  loadCustomer(){
    this.meService.getMyProfileData().subscribe((customerData)=>{
      this.customer = customerData;
      this.loadOrdersForPage();
    });
  }
  loadOrdersForPage() {
    this.orderService.getOrdersForPage(this.page, this.pageSize, this.customer.id).subscribe(
      (paginationDto: Pagination<Order>) => {
        console.log(paginationDto);
        this.orders = paginationDto.items;
        this.collectionSize = paginationDto.totalItemCount;
        window.scrollTo(0,0);
        console.log('myOrders are: ', this.orders);
        console.log('collectionSize:', this.collectionSize);
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }
  previousPage(){
    if (this.page > 0) {
      this.page -= 1;
      this.loadOrdersForPage();
    }
  }
  nextPage(){
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.loadOrdersForPage();
    }
  }

}
