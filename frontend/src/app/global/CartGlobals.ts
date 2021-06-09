import {Injectable} from '@angular/core';
import {Product} from '../dtos/product';
import {CartService} from '../services/cart.service';

@Injectable({
  providedIn: 'root'
})
export class CartGlobals {
  constructor(private service: CartService) {
  }
  public getCart() {
    let cart = localStorage.getItem('cart');
    if (!cart) {
      localStorage.setItem('cart', JSON.stringify([]));
      cart = localStorage.getItem('cart');
    }
    return JSON.parse(cart);
  }

  getCartSize() {
    const cart = this.getCart();
    return cart.length !== undefined ? cart.length : 0;
  }

  updateCart(item, quantity) {
    const cart = this.getCart();
    if (this.containsProductAtIndex(item) === -1) {
      item['quantity'] = 1;
      cart.push(item);
    } else {
      cart[this.containsProductAtIndex(item)]['quantity'] = quantity;
    }
    this.setCart(cart);
  }

  addToCart(item) {
    const cart = this.getCart();
    if (this.containsProductAtIndex(item) === -1) {
      item['quantity'] = 1;
      cart.push(item);
    } else {
      const quantity = cart[this.containsProductAtIndex(item)]['quantity'];
      cart[this.containsProductAtIndex(item)]['quantity'] = quantity + 1;
    }
    this.setCart(cart);
  }

  deleteFromCart(product: Product) {
    const cart = this.getCart();
    const newCart = [];

    cart.forEach((item) => {
      if (item.id !== product.id) {
        newCart.push(item);

      }
    });
    this.setCart(newCart);
  }

  isCartEmpty() {
    return this.getCartSize() === 0;
  }

  resetCart() {
    localStorage.setItem('cart', JSON.stringify([]));
  }

  setCart(cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
  }

  private containsProductAtIndex(product: Product) {
    const cart = this.getCart();
    for (let i = 0; i < this.getCartSize(); i++) {
      if (cart[i].id === product.id) {
        return i;
      }
    }
    return -1;
  }
}
