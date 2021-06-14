import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../dtos/customer';
import {MeService} from '../../../services/me.service';
import {CartService} from '../../../services/cart.service';
import {CartGlobals} from '../../../global/cartGlobals';
import {Product} from '../../../dtos/product';

@Component({
  selector: 'app-shop-checkout',
  templateUrl: './shop-checkout.component.html',
  styleUrls: ['./shop-checkout.component.scss']
})
export class ShopCheckoutComponent implements OnInit {

  customer: Customer;
  products: Product[];

  constructor(private meService: MeService, private cartService: CartService, private cartGlobals: CartGlobals) {
  }

  ngOnInit(): void {
    this.fetchCustomer();
    this.products = this.cartGlobals.getCart();
  }

  getStreetAddress(): string {
    return this.customer.address.street + ' ' + this.customer.address.houseNumber + (this.customer.address.doorNumber
      ? '/' + this.customer.address.doorNumber : '');
  }

  getCartSize() {
    return this.cartGlobals.getCartSize();
  }

  getTotalPrice() {
    let price = 0;
    for (const item of this.products) {
      price += item.price;
    }
    return price;
  }

  getTotalPriceWithoutTaxes() {
    let price = 0;
    for (const item of this.products) {
      price += item.price - ((item.price / (item.taxRate.percentage)));
    }
    return price;
  }

  getTotalTaxes() {
    let price = 0;
    for (const item of this.products) {
      price += (item.price / (item.taxRate.percentage));
    }
    return price;
  }

  private fetchCustomer() {
    this.meService.getMyProfileData().subscribe(
      (customer: Customer) => {
        this.customer = customer;
      }
    );
  }

}
