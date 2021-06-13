import {Injectable} from '@angular/core';
import {Product} from '../dtos/product';
import {Cart} from '../dtos/cart';
import {ProductService} from '../services/product.service';

@Injectable({
  providedIn: 'root'
})
export class CartGlobals {
  constructor(private productService: ProductService) {
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
    if (this.containsProductAtIndex(item) === -1 && cart.length <= 20) {
      item['cartItemQuantity'] = 1;
      cart.push(item);
    } else {
      cart[this.containsProductAtIndex(item)]['cartItemQuantity'] = quantity;
    }
    this.setCart(cart);
  }

  updateCartId(item, quantity, cartItemId) {
    const cart = this.getCart();
    if (this.containsProductAtIndex(item) === -1 && cart.length <= 20) {
      item['cartItemQuantity'] = 1;
      item['cartItemId'] = cartItemId;
      cart.push(item);
    } else {
      cart[this.containsProductAtIndex(item)]['cartItemQuantity'] = quantity;
      cart[this.containsProductAtIndex(item)]['cartItemId'] = cartItemId;
    }
    this.setCart(cart);
  }


  updateTotalCart(cart) {
    const cartToUpdate = this.getCart();
    cart.forEach((item) => {
      cartToUpdate.forEach((cartItem) => {
        if (item.productId === cartItem.id) {
          if (item.quantity === cartItem.quantity) {
            this.updateCartId(cartItem, item.quantity, item.id);
          }
        }
      });
    });
  }

  updateCartItem(cart: Cart) {
    const updatedCart = this.getCart();
    cart.cartItems.forEach((cartItem) => {
      updatedCart.forEach((updatedCartItem) => {
        if (updatedCartItem.id === cartItem.productId) {
          updatedCartItem.cartItemId = cartItem.id;
          this.updateCartId(updatedCartItem, cartItem.quantity, cartItem.id);
        }
      });
    });
    this.updateTotalCart(updatedCart);
  }

  addToCart(item) {
    const cart = this.getCart();
    if (cart.length <= 20) {
      item['cartItemQuantity'] = 1;
      cart.push(item);
      this.setCart(cart);
    }
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
    if (cart.length <= 20) {
      localStorage.setItem('cart', JSON.stringify(cart));
    }
  }

  containsProductAtIndex(product: Product) {
    const cart = this.getCart();
    for (let i = 0; i < this.getCartSize(); i++) {
      if (cart[i].id === product.id) {
        return i;
      }
    }
    return -1;
  }

  containsProductIdAtIndex(id: number) {
    const cart = this.getCart();
    for (let i = 0; i < this.getCartSize(); i++) {
      if (cart[i].id === id) {
        return i;
      }
    }
    return -1;
  }

}
