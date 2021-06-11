import {AfterContentInit, Component, OnInit} from '@angular/core';
import {CartService} from '../../../services/cart.service';
import {faAngleLeft, faMinus, faMoneyCheckAlt} from '@fortawesome/free-solid-svg-icons';
import {Product} from '../../../dtos/product';
import {Globals} from '../../../global/globals';
import {Router} from '@angular/router';
import {CartGlobals} from '../../../global/cartGlobals';
import {Cart} from '../../../dtos/cart';
import {CartItem} from '../../../dtos/cartItem';

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.scss']
})
export class ShopCartComponent implements OnInit, AfterContentInit {
  error = false;
  errorMessage = '';
  faCheckout = faMoneyCheckAlt;
  faDelete = faMinus;
  faBack = faAngleLeft;
  products: Product[];
  cart: Cart;
  cartItems: CartItem[];

  constructor(private cartService: CartService, private globals: Globals, private cartGlobals: CartGlobals, private router: Router) {
  }

  ngOnInit(): void {
    this.products = this.cartGlobals.getCart();
    this.cartItems = [];
  }

  ngAfterContentInit() {
     this.fetchCart();
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
    this.cartGlobals.deleteFromCart(product);
    this.deleteCartItem(new CartItem(product.id, product.quantity));
    this.products = newProductList;
    this.calcAmount();
  }

  deleteCartItem(cartItem: CartItem) {
    this.cartService.deleteCart(cartItem).subscribe(() => {
    }, error => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  updateQuantity(event, product) {
    this.cartService.updateToCart(new CartItem(product.id, event.target.value)).subscribe( (item) => {
      console.log(item);
      this.cartGlobals.appendMissingItems(item);
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }


  cartIsEmpty() {
    return this.cartGlobals.isCartEmpty();
  }

  changedTotal(index: number, event) {
    this.products[index].quantity = event.target.value;
    this.cartGlobals.updateCart(this.products[index], event.target.value);
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

  private fetchCart() {
    this.cartService.getCart().subscribe((items) => {
      if (items.cartItems.length !== 0) {
        this.cartGlobals.updateTotalCart(items);
        this.products = this.cartGlobals.getCart();
      }
    });
  }

}
