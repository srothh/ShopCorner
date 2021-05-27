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
  getProducts(page: number, pageCount): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.messageBaseUri + '/?page='+ page + '&page_count='+pageCount);
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
  addProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.messageBaseUri , product);
  }
  /**
   * updates an existing product in the backend and assigns relationship to a category with the given categoryId
   * and the tax-rate with the given taxRateId
   */
  updateProduct(productId: number, product: Product): Observable<void> {
      return this.httpClient.put<void>(this.messageBaseUri + '/'+ productId, product);
  }

  /**
   * retrieves the total number of products
   */
  getNumberOfProducts(): Observable<number> {
    return this.httpClient.get<number>(this.messageBaseUri);
  }

  /**
   * deletes a specific product with the given Id
   */
  deleteProduct(productId: number): Observable<void> {
    return this.httpClient.delete<void>(this.messageBaseUri + '/'+ productId);
  }




}
