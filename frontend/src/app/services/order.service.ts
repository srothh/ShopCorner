import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Order} from '../dtos/order';
import {CancellationPeriod} from '../dtos/cancellationPeriod';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Pagination} from '../dtos/pagination';
import {CustomerAuthService} from './auth/customer-auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private orderBaseURI: string = this.globals.backendUri + '/orders';

  constructor(private httpClient: HttpClient, private globals: Globals,
              private operatorAuthService: OperatorAuthService,
              private customerAuthService: CustomerAuthService) {
  }

  /** Places a new order
   *
   * @param order dto containing information on the order
   * @return the order dto as returned from the backend
   */
  placeNewOrder(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(this.orderBaseURI, order, {withCredentials: true});
  }

  /** sets the cancellation period for orders.
   *
   * @param cancellationPeriod the dto containing the information on the cancellation period
   * @return the dto as returned from the backend
   */
  setCancellationPeriod(cancellationPeriod: CancellationPeriod): Observable<CancellationPeriod> {
    return this.httpClient.put<CancellationPeriod>(this.orderBaseURI + '/settings',
      cancellationPeriod, {headers: this.getHeadersForOperator()});
  }

  /** gets the cancellation period from the backend
   *
   * @return the cancellation period
   */
  getCancellationPeriod(): Observable<CancellationPeriod> {
    return this.httpClient.get<CancellationPeriod>(this.orderBaseURI + '/settings');
  }

  /*TO-DELETE - because this method has already been written */
  getOrdersForPage(page: number, pageSize: number, customerId: number): Observable<Pagination<Order>> {
    const options = {
     params: new HttpParams()
        .set('customerId', `${customerId}`)
        .set('page', `${page}`)
        .set('page_count', `${pageSize}`),
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`)
    };
    return this.httpClient.get<Pagination<Order>>(this.orderBaseURI, options);
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

  private getHeadersForCustomer(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`);
  }
}
