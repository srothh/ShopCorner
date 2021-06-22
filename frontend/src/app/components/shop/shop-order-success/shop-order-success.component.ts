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
import {PromotionService} from '../../../services/promotion.service';
import {Promotion} from '../../../dtos/promotion';

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
  invoiceDto: Invoice;
  cart: Cart;
  confirmedPayment: ConfirmedPayment;
  alreadyOrdered: boolean;
  promotionId;
  promotion: Promotion;

  constructor(private paypalService: PaypalService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private cartGlobals: CartGlobals,
              private orderService: OrderService,
              private meService: MeService,
              private cartService: CartService,
              private promotionService: PromotionService) {
  }

  ngOnInit(): void {
    this.products = this.cartGlobals.getCart();
    this.fetchCustomer();
    this.activatedRoute.queryParams.subscribe(params => {
      this.paymentId = params['paymentId'];
      this.payerId = params['PayerID'];
      this.promotionId = params['promotion'];
      this.confirmedPayment = new ConfirmedPayment(0, this.paymentId, this.payerId);
      this.paypalService.confirmPayment(this.confirmedPayment).subscribe((finalisedPaymentData) => {
        if (finalisedPaymentData.includes('Payment successful')) {
          this.paymentSucceeded = true;
          this.placeNewOrder();
          this.cartGlobals.resetCart();
          // after payment -> discard paymentId and payerID

        }
      }, error => {
        this.paymentSucceeded = false;
      });
    });
  }

  getPromotion() {
    this.promotionService.getPromotionByCode(this.promotionId).subscribe((promotion: Promotion) => {
      this.promotion = promotion;
    });
  }

  getCartSize() {
    return this.cartGlobals.getCartSize();
  }

  placeNewOrder() {
    this.creatInvoiceDto();
    const order: Order = new Order(0, this.invoiceDto, this.customer, null);
    this.orderService.placeNewOrder(order).subscribe(() => {
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
      }
    );
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
    this.invoiceDto.customerId = this.customer.id;
    this.invoiceDto.invoiceType = InvoiceType.customer;
  }

  getCartItems() {
    this.cartService.getCart().subscribe((cart: Cart
    ) => {
      this.cart = cart;
    });
  }

  goToHome() {
    this.router.navigate(['/home']).then();
  }
}
