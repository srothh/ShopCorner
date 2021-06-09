import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Product} from '../dtos/product';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartBaseUri: string = this.globals.backendUri + '/carts';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  // TODO: {withCredentials: true} throws an CORS error
  addProductsToCart(products: Product[]): Observable<any> {
      return this.httpClient.put<any>(this.cartBaseUri, products);
  }

  getSessionAuthentication(products: Product[]): Observable<any> {
    return this.httpClient.get<any>(this.cartBaseUri);
  }

  private getCookie() {
    const name = 'sessionId';
    const dc = document.cookie;
    const prefix = name + '=';
    let begin = dc.indexOf('; ' + prefix);
    if (begin === -1) {
      begin = dc.indexOf(prefix);
      if (begin !== 0) {
        return false;
      }
    } else {
      begin += 2;
      let end = document.cookie.indexOf(';', begin);
      if (end === -1) {
        end = dc.length;
      }
      return true;
    }
  }

}

