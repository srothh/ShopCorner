import { Component, OnInit } from '@angular/core';
import {CartService} from '../../../services/cart.service';
import {faMinus, faMoneyCheckAlt} from '@fortawesome/free-solid-svg-icons';
import {Product} from '../../../dtos/product';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.scss']
})
export class ShopCartComponent implements OnInit {
  error = false;
  errorMessage = '';
  faCheckout = faMoneyCheckAlt;
  faDelte = faMinus;
  products: Product[];
  quantity: number;
  map = [{item: {}, quantity: 1}];


  constructor(private cartService: CartService, private globals: Globals) { }

  ngOnInit(): void {
    this.products = [];
    this.globals.getCart().forEach((product) => {
        this.products.push(product);
    });
    this.quantity = 1;
  }


  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }

  removeFromCart(product: Product) {
    const id = this.products.indexOf(product);
    const newProductList = [];
    this.products.forEach((item) => {
      if (item.id !== product.id) {
        newProductList.push(item);
      }
    });
    this.globals.deleteFromCart(product);
    this.products = newProductList;
  }

  cartIsEmpty() {
    return this.globals.getCartSize() === 0;
  }

  updateQuantity(index: number, product: Product, event) {
    const value = Number(event.target.value);
    this.map[index] = {item: product, quantity: value};
    return value;
  }

  getQuantity(index: number, product: Product) {
    return this.map[index].quantity;
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }



}
