import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Customer} from '../dtos/customer';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Pagination} from '../dtos/pagination';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customerBaseUri: string = this.globals.backendUri + '/customers';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Register a new customer.
   *
   * @param customer The customer dto to register
   * @return The customer dto as received from the backend
   */
  addCustomer(customer: Customer): Observable<Customer> {
    console.log('Create new Customer', customer);
    return this.httpClient.post<Customer>(this.customerBaseUri, customer);
  }

  /**
   * Retrieve a page of customers from the backend.
   *
   * @param page the number of the page to fetch
   * @param pageCount the size of the page to be fetched
   * @return The customers retrieved from the backend
   */
  getAllCustomersForPage(page: number, pageCount: number): Observable<Pagination<Customer>> {
    console.log('Get customers for page', page);
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount));

    return this.httpClient.get<Pagination<Customer>>(this.customerBaseUri, {params, headers: this.getHeadersForOperator()});
  }

  /**
   * Retrieves the amount of customers persisted in the backend.
   *
   * @return The number of registered customer accounts
   */
  getCustomerCount(): Observable<number> {
    console.log('Get customer count');
    return this.httpClient.get<number>(this.customerBaseUri + '/count', {headers: this.getHeadersForOperator()});
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }
}
