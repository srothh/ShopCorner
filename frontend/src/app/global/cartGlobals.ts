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
      item['quantity'] = 1;
      cart.push(item);
    } else {
      cart[this.containsProductAtIndex(item)]['quantity'] = quantity;
    }
    this.setCart(cart);
  }


  updateTotalCart(cart: Cart) {
    const cartToUpdate = this.getCart();
    cart.cartItems.forEach((item) => {
      cartToUpdate.forEach( (cartItem) => {
          if (item.productId === cartItem.id) {
            if (item.quantity === cartItem.quantity) {
              this.updateCart(cartItem, item.quantity);
            }
          }
        });
    });
  }

  appendMissingItems(cart: Cart) {
    let cartToExtend = this.getCart();
    const cartItem = [];
    if (cartItem.length !== this.getCartSize()) {
      cart.cartItems.forEach((items) => {
        cartItem.push(items);
        if (this.getCartSize() > cartItem.length) {
          this.resetCart();
          cartToExtend = this.getCart();
          this.productService.getProductById(items.productId).subscribe((product) => {
            product.quantity = items.quantity;
            this.setCart(cartToExtend);
          });
        }
        if (this.containsProductIdAtIndex(items.productId) === -1) {
          this.productService.getProductById(items.productId).subscribe((product) => {
            product.quantity = items.quantity;
            cartToExtend.push(product);
            this.setCart(cartToExtend);
          });
        }
      });
    }
  }

  addToCart(item) {
    const cart = this.getCart();
    if (cart.length <= 20) {
      item['quantity'] = 1;
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
