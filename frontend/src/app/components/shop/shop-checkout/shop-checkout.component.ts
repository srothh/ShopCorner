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
import {InvoiceType} from '../../../dtos/invoiceType.enum';
import {CancellationPeriod} from '../../../dtos/cancellationPeriod';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PromotionService} from '../../../services/promotion.service';
import {Promotion} from '../../../dtos/promotion';
import {ProductService} from '../../../services/product/product.service';

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
  cancellationPeriod: CancellationPeriod;
  loading: boolean;
  orderForm: FormGroup;
  promotion: Promotion = null;
  promotionError = false;
  promotionErrorMessage = '';
  error = false;
  errorMessage = '';

  constructor(private meService: MeService, private cartService: CartService, private cartGlobals: CartGlobals,
              private orderService: OrderService, private paypalService: PaypalService, private router: Router,
              private formBuilder: FormBuilder, private promotionService: PromotionService) {
  }

  ngOnInit(): void {
    this.orderForm = this.formBuilder.group({
      promoCode: ['']
    });
    this.fetchCustomer();
    this.products = this.cartGlobals.getCart();
    this.getCancellationPeriod();
  }

  getStreetAddress(): string {
    return this.customer.address.street + ' ' + this.customer.address.houseNumber + (this.customer.address.doorNumber
      ? '/' + this.customer.address.doorNumber : '');
  }

  getTotalPriceWithPromotion() {
    if (this.promotion) {
      return this.getTotalPrice() - this.promotion.discount;
    } else {
      return this.getTotalPrice();
    }
  }

  getCartSize() {
    return this.cartGlobals.getCartSize();
  }

  getTotalPrice() {
    return this.getTotalPriceWithoutTaxes() + this.getTotalTaxes();
  }

  getTotalPriceWithoutTaxes(): number {
    let subtotal = 0;
    this.products.forEach((item) => {
      subtotal += (item.price * item.cartItemQuantity);
    });
    return subtotal;
  }

  getTotalTaxes(): number {
    let tax = 0;
    this.products.forEach((item) => {
      tax += item.price * (item.taxRate.calculationFactor - 1) * item.cartItemQuantity;
    });
    return tax;
  }

  vanishError() {
    this.error = false;
  }

  getCartItems() {
    this.cartService.getCart().subscribe((cart: Cart
    ) => {
      this.cart = cart;
    }, error => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  proceedToPay() {
    const mappedProducts = this.products.map(ProductService.productMapper);
    mappedProducts.forEach((product) => {
      if (product.hasExpiration && product.hasExpired) {
        this.error = true;
        this.errorMessage = `Das Produkt "${product.name}" ist nicht verfügbar. Bitte setzen Sie ihren Einkauf ohne dieses Produkt fort.`;
      }
    });
    if (!this.error) {
      this.createInvoiceDto();
      const order: Order = new Order(0, this.invoiceDto, this.customer, this.promotion);
      this.paypalService.createPayment(order).subscribe((redirectUrl) => {
        window.location.href = redirectUrl;
        this.loading = true;
      });
    }
  }
  createInvoiceDto() {
    this.invoiceDto = new Invoice();
    this.invoiceDto.invoiceNumber = '';

    for (const item of this.products) {
      if (item !== undefined) {
        const invItem = new InvoiceItem(new InvoiceItemKey(item.id), item, item.cartItemQuantity);
        this.invoiceDto.items.push(invItem);
      }
    }
    this.invoiceDto.amount = +this.getTotalPriceWithPromotion().toFixed(2);
    this.invoiceDto.date = formatDate(new Date(), 'yyyy-MM-ddTHH:mm:ss', 'en');
    this.invoiceDto.customerId = this.customer.id;
    this.invoiceDto.invoiceType = InvoiceType.customer;
  }

  getPromotion() {
    this.promotionService.getPromotionByCode(this.orderForm.controls.promoCode.value).subscribe((promotion: Promotion) => {
      if (this.isValidCode(promotion)) {
        this.promotionError = false;
        this.promotionErrorMessage = '';
        this.promotion = promotion;
      }
    }, (error) => {
      this.promotionError = true;
      this.promotionErrorMessage = error;
    });
  }

  private isValidCode(promotion: Promotion) {
    if (this.getTotalPrice() >= promotion.minimumOrderValue && new Date(Date.now()) <= new Date(promotion.expirationDate)) {
      return true;
    } else if (this.getTotalPrice() >= promotion.minimumOrderValue) {
      this.promotionError = true;
      this.promotionErrorMessage = 'Gutscheincode abgelaufen!';
      return false;
    } else {
      this.promotionError = true;
      this.promotionErrorMessage = 'Mindestbestellwert nicht erreicht!!';
      return false;
    }
  }

  private fetchCustomer() {
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

  private getCancellationPeriod() {
    this.orderService.getCancellationPeriod().subscribe((cancellationPeriod: CancellationPeriod) => {
      this.cancellationPeriod = cancellationPeriod;
    }, (error => {
      this.error = true;
      this.errorMessage = error;
    }));
  }


}
