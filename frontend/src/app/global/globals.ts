import {Injectable} from '@angular/core';
import {Product} from '../dtos/product';

@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = this.findBackendUrl();

  public getCart() {
    let cart = localStorage.getItem('cart');
    if ( !cart) {
      localStorage.setItem('cart', JSON.stringify([]));
      cart = localStorage.getItem('cart');
    }
    return JSON.parse(cart);
  }

  getCartSize() {
    const cart = this.getCart();
    return cart.length !== undefined ? cart.length : 0;
  }

  getNumberOfCartItems() {
    const cart = this.getCart();
    let numberOfItems = 0;
    if (this.getCartSize() !== 0) {
      for (let i = 0; i < this.getCartSize(); i++) {
        numberOfItems += cart[i].quantity;
      }
      return numberOfItems;
    } else {
      return 0;
    }
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

  private findBackendUrl(): string {
    if (window.location.port === '4200') { // local `ng serve`, backend at localhost:8080
      return 'http://localhost:8080/api/v1';
    } else {
      // assume deployed somewhere and backend is available at same host/port as frontend
      return window.location.protocol + '//' + window.location.host + window.location.pathname + 'api/v1';
    }
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


