import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../dtos/customer';
import {MeService} from '../../../services/me.service';
import {CartService} from '../../../services/cart.service';
import {CartGlobals} from '../../../global/cartGlobals';
import {Product} from '../../../dtos/product';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {InvoiceItemKey} from '../../../dtos/invoiceItemKey';
import {formatDate} from '@angular/common';
import {Cart} from '../../../dtos/cart';
import {OrderService} from '../../../services/order.service';
import {Order} from '../../../dtos/order';
import {Address} from '../../../dtos/address';
import {PaypalService} from '../../../services/paypal.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-shop-checkout',
  templateUrl: './shop-checkout.component.html',
  styleUrls: ['./shop-checkout.component.scss']
})
export class ShopCheckoutComponent implements OnInit {

  customer: Customer = new Customer(0, '', '', '', '', new Address(0, '', 0, '', 0, '')
    , '');
  products: Product[];
  invoiceDto: Invoice;
  cart: Cart;
  loading: boolean;

  constructor(private meService: MeService, private cartService: CartService, private cartGlobals: CartGlobals,
              private orderService: OrderService, private paypalService: PaypalService, private router: Router) {
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

  getCartItems() {
    this.cartService.getCart().subscribe((cart: Cart
    ) => {
      this.cart = cart;
    });
  }

  creatInvoiceDto() {
    this.invoiceDto = new Invoice();
    this.invoiceDto.invoiceNumber = '';

    for (const item of this.products) {
      if (item !== undefined) {
        const invItem = new InvoiceItem(new InvoiceItemKey(item.id), item, item.cartItemQuantity);
        this.invoiceDto.items.push(invItem);
      }

    }
    this.invoiceDto.amount = +this.getTotalPrice().toFixed(2);
    this.invoiceDto.date = formatDate(new Date(), 'yyyy-MM-ddTHH:mm:ss', 'en');
  }

  placeNewOrder() {
    this.creatInvoiceDto();
    const order: Order = new Order(0, this.invoiceDto, this.customer);

    this.paypalService.createPayment(order).subscribe((redirectUrl) => {
      window.location.href = redirectUrl;
      this.loading = true;
    });
    /*this.orderService.placeNewOrder(order).subscribe(() => {
    });*/
  }

  private fetchCustomer() {
    this.meService.getMyProfileData().subscribe(
      (customer: Customer) => {
        this.customer = customer;
        this.getCartItems();
      }
    );
  }

}
