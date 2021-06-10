import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Product} from '../dtos/product';
import {Observable} from 'rxjs';
import {Cart} from '../dtos/cart';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartBaseUri: string = this.globals.backendUri + '/carts';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

/*
  addProductsToCart(products: Cart): Observable<Cart> {
    return this.httpClient.post<Cart>(this.cartBaseUri, products, {withCredentials: true});
  }*/

  productsToCart(products: Cart): Observable<Cart> {
    return this.httpClient.put<Cart>(this.cartBaseUri, products, {withCredentials: true});
  }
}

