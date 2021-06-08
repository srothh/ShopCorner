import { Component, OnInit } from '@angular/core';
import {CartService} from '../../../services/cart.service';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {
  faCross, faCrosshairs, faMinus,
  faMoneyBill,
  faMoneyBillWaveAlt,
  faMoneyCheck,
  faMoneyCheckAlt,
  faTrash,
  faWallet
} from '@fortawesome/free-solid-svg-icons';
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
  constructor(private cartService: CartService, private globals: Globals) { }

  ngOnInit(): void {
    this.products = [];
    this.globals.getCart().forEach((item) => {
      this.products.push(item);
      });
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

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

}
