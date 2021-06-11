import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Product} from '../dtos/product';
import {Observable} from 'rxjs';
import {Cart} from '../dtos/cart';
import {CartItem} from '../dtos/cartItem';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartBaseUri: string = this.globals.backendUri + '/carts';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  deleteCart(cartItem: CartItem): Observable<any> {
    return this.httpClient.delete<any>(this.cartBaseUri + '/' + cartItem.productId, {withCredentials: true});
  }

  getCart(): Observable<Cart> {
    return this.httpClient.get<Cart>(this.cartBaseUri, {withCredentials: true});
  }

  addProductsToCart(cartItem: CartItem): Observable<Cart> {
    return this.httpClient.post<Cart>(this.cartBaseUri, cartItem, {withCredentials: true});
  }

  updateToCart(cartItem: CartItem): Observable<Cart> {
    return this.httpClient.put<Cart>(this.cartBaseUri, cartItem, {withCredentials: true});
  }
}

