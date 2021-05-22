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
   * Loads a product with the given Id, if it's present in the backend
   */
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.messageBaseUri + '/' + id);
  }
  /**
   * Adds a new Product in the backend and assigns relationship to category with the given categoryId
   * and the taxRateId
   */
  addProduct(product: Product, categoryId: number, taxRateId: number): Observable<Product> {
    if (isNaN(categoryId)||categoryId == null) {
      return this.httpClient.post<Product>(this.messageBaseUri + '/categories/tax-rates/' + taxRateId, product);
    }else {
      return this.httpClient.post<Product>(this.messageBaseUri + '/categories/' + categoryId + '/tax-rates/' + taxRateId, product);
    }
  }
  /**
   * updates an existing product in the backend and assigns relationship to a category with the given categoryId
   * and the tax-rate with the given taxRateId
   */
  updateProduct(productId: number,product: Product, categoryId: number, taxRateId: number): Observable<void> {
    if (isNaN(categoryId)||categoryId == null) {
      console.log('categoryId', categoryId);
      return this.httpClient.put<void>(this.messageBaseUri +'/' + productId + '/categories/tax-rates/' + taxRateId, product);
    }else {
      console.log('categoryId', categoryId);
      return this.httpClient.put<void>(this.messageBaseUri + '/'+ productId+ '/categories/' + categoryId + '/tax-rates/' +
        taxRateId, product);
    }
  }




}
