import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
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

  /**
   * Removes a cartItem from the cart.
   *
   * @param cartItem the item to be deleted
   */
  deleteCart(cartItem: CartItem): Observable<any> {
    return this.httpClient.delete<any>(this.cartBaseUri + '/' + cartItem.id, {withCredentials: true});
  }

  /**
   * Retrieves the cart from the backend.
   *
   * @return Cart retrieved from the backend
   */
  getCart(): Observable<Cart> {
    return this.httpClient.get<Cart>(this.cartBaseUri, {withCredentials: true});
  }

  /**
   * Adds the cartItem to a cart and retrieves the cart from the backend.
   *
   * @param cartItem the item to be added
   * @return Cart retrieved from the backend
   */
  addProductsToCart(cartItem: CartItem): Observable<Cart> {
    return this.httpClient.post<Cart>(this.cartBaseUri, cartItem, {withCredentials: true});
  }

  /**
   * Updates the cartItem of a cart and retrieves the updated cart from the backend.
   *
   * @param cartItem the item to be updated
   * @return Cart retrieved from the backend
   */
  updateToCart(cartItem: CartItem): Observable<Cart> {
    return this.httpClient.put<Cart>(this.cartBaseUri, cartItem, {withCredentials: true});
  }
}

