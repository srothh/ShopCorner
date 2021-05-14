import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../dtos/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private messageBaseUri: string = this.globals.backendUri + '/products';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all products from the backend
   */
  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.messageBaseUri);
  }
  /**
   * Adds a new Product in the backend and assigns relationship to category with the given categoryId
   * and the taxRateID
   */
  addProduct(product: Product, categoryId: number, taxRateId: number): Observable<Product> {
    if (isNaN(categoryId)||categoryId == null) {
      return this.httpClient.post<Product>(this.messageBaseUri + '/categories/tax-rates/' + taxRateId, product);
    }else {
      console.log('categorID is: ', categoryId);
      return this.httpClient.post<Product>(this.messageBaseUri + '/categories/' + categoryId + '/tax-rates/' + taxRateId, product);
    }
  }



}
