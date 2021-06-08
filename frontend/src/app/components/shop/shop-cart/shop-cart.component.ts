import { Component, OnInit } from '@angular/core';
import {CartService} from '../../../services/cart.service';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {faMoneyBill, faMoneyBillWaveAlt, faMoneyCheck, faMoneyCheckAlt, faWallet} from '@fortawesome/free-solid-svg-icons';
import {Product} from '../../../dtos/product';

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.scss']
})
export class ShopCartComponent implements OnInit {
  error = false;
  errorMessage = '';
  faCheckout = faMoneyCheckAlt;
  products: Product[];
  constructor(private cartService: CartService) { }

  ngOnInit(): void {

  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

}
