import {AfterContentInit, Component, OnInit} from '@angular/core';
import {CartService} from '../../../services/cart/cart.service';
import {faAngleLeft, faMinus, faMoneyCheckAlt} from '@fortawesome/free-solid-svg-icons';
import {Product} from '../../../dtos/product';
import {Globals} from '../../../global/globals';
import {Router} from '@angular/router';
import {CartGlobals} from '../../../global/cartGlobals';
import {Cart} from '../../../dtos/cart';
import {CartItem} from '../../../dtos/cartItem';
import {ProductService} from '../../../services/product/product.service';

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
  onClick = false;

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

  removeFromCart(id) {
    const product = this.cartGlobals.getCart()[id];
    const newProductList = [];
    this.products.forEach((item) => {
      if (item.id !== product.id) {
        newProductList.push(item);
      }
    });
    this.cartGlobals.deleteFromCart(product);
    const itemToDelete = new CartItem(product.id, product.cartItemQuantity);
    itemToDelete.id = product.cartItemId;
    this.deleteCartItem(itemToDelete);
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

  updateLocalQuantity(event, id) {
    this.onClick = true;
    const product = this.cartGlobals.getCart()[id];
    this.cartGlobals.updateCartId(product, event.target.value, product.cartItemId);
  }

  updateQuantity(event, id) {
    if (this.onClick) {
      const product = this.cartGlobals.getCart()[id];
      this.onClick = false;
      const cartItem = new CartItem(product.id, event.target.value);
      cartItem.id = product.cartItemId;
      this.cartService.updateToCart(cartItem).subscribe((item) => {
        this.cartGlobals.updateCartItem(item);
      }, (error) => {
        this.error = true;
        this.errorMessage = error;
      });
    }
  }


  cartIsEmpty() {
    return this.cartGlobals.isCartEmpty();
  }

  changedTotal(index: number, event) {
    this.products[index].cartItemQuantity = event.target.value;
    this.cartGlobals.updateCart(this.products[index], event.target.value);
    this.calcAmount();
  }

  calcAmount() {
    document.getElementById('subtotal').innerText = ShopCartComponent.sanitizeHTML((this.calcSubtotal() + ''));
    document.getElementById('tax').innerText = ShopCartComponent.sanitizeHTML(this.calcTax() + '');
    document.getElementById('total').innerText = ShopCartComponent.sanitizeHTML(this.calcTotal() + '');
  }

  calcSubtotal() {
    let subtotal = 0;
    this.products.forEach((item) => {
      subtotal = subtotal + (item.price * item.cartItemQuantity);
    });
    return subtotal.toFixed(2);
  }

  calcTax() {
    let tax = 0;
    this.products.forEach((item) => {
      tax += item.price * (item.taxRate.calculationFactor - 1) * item.cartItemQuantity;
    });
    return tax.toFixed(2);
  }

  calcTotal() {
    return (Number(this.calcSubtotal()) + Number(this.calcTax())).toFixed(2);
  }

  routeToDetailView(product: Product) {
    this.router.navigate(['products/' + product.id]).then();

  }

  routeToCheckout() {
    const mappedProducts = this.products.map(ProductService.productMapper);
    mappedProducts.forEach((product) => {
      if (product.hasExpiration && product.hasExpired) {
        this.error = true;
        this.errorMessage = `Das Produkt "${product.name}" ist nicht verfügbar. Bitte setzen Sie ihren Einkauf ohne dieses Produkt fort.`;
      }
    });
    if (!this.error) {
      this.router.navigate(['checkout']);
    }
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

  private static sanitizeHTML(str: string) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerText;
  }

  private fetchCart() {
    this.cartService.getCart().subscribe((items) => {
      this.cartGlobals.updateCartItem(items);

    });
  }
}
