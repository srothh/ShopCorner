import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../../dtos/product';
import {OperatorAuthService} from '../auth/operator-auth.service';
import {Pagination} from '../../dtos/pagination';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productBaseUri: string = this.globals.backendUri + '/products';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Get page of products from the backend
   *
   * @return observable of type Pagination<Product>
   */
  getProducts(page: number, pageCount, name = '', sortBy = 'id', categoryId: number = -1): Observable<Pagination<Product>> {
    const params = new HttpParams()
      .set('page', String(page))
      .set('page_count', String(pageCount))
      .set('name', name)
      .set('category_id', String(categoryId))
      .set('sortBy', sortBy);
    console.log(params.toString());

    return this.httpClient.get<Pagination<Product>>(this.productBaseUri, {params});
  }

  /**
   * Loads a product with the given Id, if it's present in the backend
   *
   * @return observable of type Product
   */
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.productBaseUri + '/' + id);
  }

  /**
   * Adds a new Product in the backend and assigns relationship to category with the given categoryId
   * and the taxRateId
   *
   * @return observable of type Product
   */
  addProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productBaseUri, product, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * updates an existing product in the backend and assigns relationship to a category with the given categoryId
   * and the tax-rate with the given taxRateId
   *
   * @return observable of type voice
   */
  updateProduct(productId: number, product: Product): Observable<void> {
    return this.httpClient.put<void>(this.productBaseUri + '/' + productId, product, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * deletes a specific product with the given Id
   *
   * @return observable of type void
   */
  deleteProduct(productId: number): Observable<void> {
    return this.httpClient.delete<void>(this.productBaseUri + '/' + productId, {
      headers: this.getHeadersForOperator()
    });
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }


}