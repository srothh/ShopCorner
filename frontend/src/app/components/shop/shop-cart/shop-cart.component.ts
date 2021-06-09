import { Component, OnInit } from '@angular/core';
import {CartService} from '../../../services/cart.service';
import {faAngleLeft, faMinus, faMoneyCheckAlt} from '@fortawesome/free-solid-svg-icons';
import {Product} from '../../../dtos/product';
import {Globals} from '../../../global/globals';
import {Router} from '@angular/router';

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.scss']
})
export class ShopCartComponent implements OnInit {
  error = false;
  errorMessage = '';
  faCheckout = faMoneyCheckAlt;
  faDelete = faMinus;
  faBack = faAngleLeft;
  products: Product[];


  constructor(private cartService: CartService, private globals: Globals, private router: Router) { }

  ngOnInit(): void {
    this.products = [];
    this.globals.getCart().forEach((product) => {
        this.products.push(product);
    });
  }

  send() {
    this.cartService.addProductsToCart([]).subscribe((items) => {
      console.log(items);
    });
  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }

  removeFromCart(product: Product) {
    const newProductList = [];
    this.products.forEach((item) => {
      if (item.id !== product.id) {
        newProductList.push(item);
      }
    });
    this.globals.deleteFromCart(product);
    this.products = newProductList;
    this.calcAmount();
  }

  cartIsEmpty() {
    return this.globals.isCartEmpty();
  }

  changedTotal(index: number, event) {
    this.products[index].quantity = event.target.value;
    this.globals.updateCart(this.products[index], event.target.value);
    this.calcAmount();
  }

  calcAmount() {
    document.getElementById('subtotal').innerText = this.sanitizeHTML((this.calcSubtotal() + ''));
    document.getElementById('tax').innerText = this.sanitizeHTML(this.calcTax() + '');
    document.getElementById('total').innerText = this.sanitizeHTML(this.calcTotal() + '');
  }

  calcSubtotal() {
    let subtotal = 0;
    this.products.forEach((item) => {
      subtotal = subtotal + (item.price * item.quantity);
    });
    return subtotal.toFixed(2);
  }

  calcTax() {
    let tax = 0;
    this.products.forEach((item) => {
      tax += item.price * (item.taxRate.calculationFactor - 1) * item.quantity;
    });
    return tax.toFixed(2);
  }

  calcTotal() {
      return (Number(this.calcSubtotal()) + Number(this.calcTax())).toFixed(2);
  }

  routeToDetailView(product: Product) {
    this.router.navigate(['products/' + product.id]).then();

  }

  routeBackToShop() {
    this.router.navigate(['home']).then();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  private sanitizeHTML(str: string) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerText;
  }


}
