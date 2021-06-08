import {Injectable} from '@angular/core';

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

  addToCart(item) {
    const cart = this.getCart();
    cart.push(item);
    this.setCart(cart);
  }


  deleteFromCart(id) {
    const cart = this.getCart();
    const newCart = [];

    cart.forEach((item) => {
      if (item.id !== id) {
        newCart.push(item);

      }
    });
    this.setCart(newCart);
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



}


