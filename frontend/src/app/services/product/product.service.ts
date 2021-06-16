import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../../dtos/product';
import {OperatorAuthService} from '../auth/operator-auth.service';
import {Pagination} from '../../dtos/pagination';
import {map} from 'rxjs/operators';
import {Category} from '../../dtos/category';
import {TaxRate} from '../../dtos/tax-rate';

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
  getProducts(page = 0, pageCount = 15, name = '', sortBy = 'id', categoryId = -1): Observable<Pagination<Product>> {
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount))
      .set(this.globals.requestParamKeys.products.name, String(name))
      .set(this.globals.requestParamKeys.products.categoryId, String(categoryId))
      .set(this.globals.requestParamKeys.products.sortBy, String(sortBy));
    console.log(params.toString());
    return this.httpClient.get<Pagination<Product>>(this.productBaseUri, {params}).pipe(
      map((pagination) => {
        pagination.items = pagination.items.map(this.productMapper);
        return pagination;
      })
    );
  }

  /**
   * Loads a product with the given Id, if it's present in the backend
   *
   * @return observable of type Product
   */
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.productBaseUri + '/' + id).pipe(
      map(this.productMapper)
    );
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

  private productMapper = (p) => new Product(p.id, p.name, p.description, p.price, p.category, p.taxRate, p.locked, p.picture, p.expiresAt, p.deleted);
}
