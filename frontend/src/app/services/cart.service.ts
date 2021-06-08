import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartBaseUri: string = this.globals.backendUri + '/carts';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /*addProductsToCart(products: Product[]): Observable<any> {
    return this.httpClient.post<any>(this.cartBaseUri,  products, {withCredentials: true});
  }*/
}
