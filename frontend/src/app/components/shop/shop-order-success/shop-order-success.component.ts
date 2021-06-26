import {Component, OnInit} from '@angular/core';
import {faCheckCircle, faTimesCircle} from '@fortawesome/free-solid-svg-icons';
import {PaypalService} from '../../../services/paypal.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CartGlobals} from '../../../global/cartGlobals';
import {Product} from '../../../dtos/product';
import {OrderService} from '../../../services/order.service';
import {Order} from '../../../dtos/order';
import {Customer} from '../../../dtos/customer';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceItem} from '../../../dtos/invoiceItem';
import {InvoiceItemKey} from '../../../dtos/invoiceItemKey';
import {formatDate} from '@angular/common';
import {InvoiceType} from '../../../dtos/invoiceType.enum';
import {Cart} from '../../../dtos/cart';
import {MeService} from '../../../services/me.service';
import {CartService} from '../../../services/cart.service';
import {ConfirmedPayment} from '../../../dtos/confirmedPayment';

@Component({
  selector: 'app-shop-order-success',
  templateUrl: './shop-order-success.component.html',
  styleUrls: ['./shop-order-success.component.scss']
})
export class ShopOrderSuccessComponent implements OnInit {
  faCheckCircle = faCheckCircle;
  faTimesCircle = faTimesCircle;
  paymentId: string;
  payerId: string;
  paymentSucceeded: boolean;
  products: Product[];
  customer: Customer;
  invoice: Invoice;
  cart: Cart;
  confirmedPayment: ConfirmedPayment;
  alreadyOrdered: boolean;

  error = false;
  errorMessage = '';

  constructor(private paypalService: PaypalService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private cartGlobals: CartGlobals,
              private orderService: OrderService,
              private meService: MeService,
              private cartService: CartService) {
  }

  ngOnInit(): void {
    this.products = this.cartGlobals.getCart();
    this.fetchCustomer();
    this.activatedRoute.queryParams.subscribe(params => {
      this.paymentId = params['paymentId'];
      this.payerId = params['PayerID'];
      this.paypalService.getConfirmedPayment(this.paymentId, this.payerId).subscribe((cp) => {
        this.alreadyOrdered = cp !== null;
        if(this.alreadyOrdered === false) {
          this.confirmPayment();
        } else {
          this.goToHome();
        }
      }, error => {
        this.error = true;
        this.errorMessage = error;
      });
    }, error => {
      this.error = true;
      this.errorMessage = error;
    });
  }
  confirmPayment(){
    this.confirmedPayment = new ConfirmedPayment(null, this.paymentId, this.payerId);
    this.paypalService.confirmPayment(this.confirmedPayment).subscribe((finalisedPaymentData) => {
      if (finalisedPaymentData.includes('Payment successful')) {
        this.paymentSucceeded = true;
        this.placeNewOrder();
      }
    }, error => {
      this.paymentSucceeded = false;
      this.error = true;
      this.errorMessage = error;
    });
  }

  placeNewOrder() {
    this.createInvoice();
    const order: Order = new Order(0, this.invoice, this.customer);
    this.orderService.placeNewOrder(order).subscribe((orderData) => {
    }, error => {
      this.paymentSucceeded = false;
      this.error = true;
      this.errorMessage = error;
    });
  }

  getTotalPrice() {
    return this.getTotalPriceWithoutTaxes() + this.getTotalTaxes();
  }

  getTotalTaxes(): number {
    let tax = 0;
    this.products.forEach((item) => {
      tax += item.price * (item.taxRate.calculationFactor - 1) * item.cartItemQuantity;
    });
    return tax;
  }

  getTotalPriceWithoutTaxes(): number {
    let subtotal = 0;
    this.products.forEach((item) => {
      subtotal += (item.price * item.cartItemQuantity);
    });
    return subtotal;
  }

  fetchCustomer() {
    this.meService.getMyProfileData().subscribe(
      (customer: Customer) => {
        this.customer = customer;
        this.getCartItems();
      }, error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
  }

  createInvoice() {
    this.invoice = new Invoice();
    this.invoice.invoiceNumber = '';

    for (const item of this.products) {
      if (item !== undefined) {
        const invItem = new InvoiceItem(new InvoiceItemKey(item.id), item, item.cartItemQuantity);
        this.invoice.items.push(invItem);
      }
    }
    this.invoice.amount = +this.getTotalPrice().toFixed(2);
    this.invoice.date = formatDate(new Date(), 'yyyy-MM-ddTHH:mm:ss', 'en');
    this.invoice.customerId = this.customer.id;
    this.invoice.invoiceType = InvoiceType.customer;
  }

  getCartItems() {
    this.cartService.getCart().subscribe((cart: Cart
    ) => {
      this.cart = cart;
    }, error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
  }

  goToHome() {
    this.cartGlobals.resetCart();
    this.router.navigate(['/home']).then();
  }
}
