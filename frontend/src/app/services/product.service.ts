import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../dtos/product';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Pagination} from '../dtos/pagination';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productBaseUri: string = this.globals.backendUri + '/products';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Loads all products from the backend
   *
   * @param page the current number the user is in
   * @param pageCount number of all entries in the page
   *
   * @return An Observable with the paginated product
   */
  getProducts(page: number, pageCount): Observable<Pagination<Product>> {
    return this.httpClient.get<Pagination<Product>>(this.productBaseUri + '/?page='+ page + '&page_count='+pageCount);
  }
  /**
   * Loads a product with the given Id, if it's present in the backend
   *
   * @param id the number of the id to search for
   *
   * @return An Observable with the requested Product specified with the id
   */
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.productBaseUri + '/' + id);
  }
  /**
   * Adds a new Product in the backend and assigns relationship to category with the given categoryId
   * and the taxRateId
   *
   * @param product the product to submit in the database
   *
   * @return An Observable with the newly added product
   */
  addProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productBaseUri , product,{
      headers: this.getHeadersForOperator()
    });
  }
  /**
   * updates an existing product in the backend and assigns relationship to a category with the given categoryId
   * and the tax-rate with the given taxRateId
   *
   * @param productId the requested id of a product to update
   * @param product the product withe updated fields
   *
   * @return An Observable wih no content
   */
  updateProduct(productId: number, product: Product): Observable<void> {
      return this.httpClient.put<void>(this.productBaseUri + '/'+ productId, product, {
        headers: this.getHeadersForOperator()
      } );
  }

  /**
   * retrieves the total number of products
   *
   * @return An Observable with the total number of products
   */
  getNumberOfProducts(): Observable<number> {
    return this.httpClient.get<number>(this.productBaseUri);
  }

  /**
   * deletes a specific product with the given Id
   *
   * @param productId the id to request for a product
   *
   * @return An Observable with no content
   */
  deleteProduct(productId: number): Observable<void> {
    return this.httpClient.delete<void>(this.productBaseUri + '/'+ productId,{
      headers: this.getHeadersForOperator()
    });
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }



}
