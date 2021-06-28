import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {CustomerAuthService} from './auth/customer-auth.service';
import {Observable} from 'rxjs';
import {Customer} from '../dtos/customer';
import {Pagination} from '../dtos/pagination';
import {Order} from '../dtos/order';
import {map} from 'rxjs/operators';
import {CustomerService} from './customer.service';


@Injectable({
  providedIn: 'root'
})
export class MeService {
  private meBaseUri: string = this.globals.backendUri + '/me';

  constructor(private httpClient: HttpClient, private globals: Globals, private customerAuthService: CustomerAuthService) {
  }

  /**
   * Retrieves the current customer's profile data
   */
  getMyProfileData(): Observable<Customer> {
    return this.httpClient.get<Customer>(this.meBaseUri, {headers: this.getHeadersForCustomer()}).pipe(
      map(CustomerService.customerMapper)
    );
  }

  getOrdersForPage(page: number, pageSize: number): Observable<Pagination<Order>> {
    const options = {
      params: new HttpParams()
        .set(this.globals.requestParamKeys.pagination.page, `${page}`)
        .set(this.globals.requestParamKeys.pagination.pageCount, `${pageSize}`),
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`)
    };
    return this.httpClient.get<Pagination<Order>>(this.meBaseUri + '/orders', options);
  }

  /**
   * Updates the specified profile in the backend.
   *
   * @param customer the profile to be updated
   */
  updateProfileData(customer: Customer): Observable<any> {
    console.log('Update profile', customer);
    return this.httpClient.put<Customer>(this.meBaseUri,  customer
      , {headers: this.getHeadersForCustomer()});
  }

  /**
   * Updates the logged in customer's password in the backend.
   *
   * @param oldPassword the password to be updated
   * @param newPassword the new password
   * @return updated password
   */
  updatePassword(oldPassword: string, newPassword: string): Observable<string>{
    console.log('Update password');
    return this.httpClient.post<string>(
      this.meBaseUri + '/password', {oldPassword, newPassword}
      , {headers: this.getHeadersForCustomer()});
  }

  /**
   * Loads invoice pdf by id from the backend for the customer
   *
   * @param id of the invoice
   * @return pdf generated from the invoice entry
   */
  getInvoiceAsPdfByIdForCustomer(id: number): Observable<any> {
    return this.httpClient.get(this.meBaseUri + '/invoices/' + id + '/pdf', this.getPdfHeadersForCustomer());
  }


  /**
   * Deletes the current customer's profile data
   */
  deleteMyAccount(): Observable<void> {
    return this.httpClient.delete<void>(this.meBaseUri, {headers: this.getHeadersForCustomer()});
  }

  private getHeadersForCustomer(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`);
  }

  private getPdfHeadersForCustomer(): any {
    return {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.customerAuthService.getToken()}`).set('Accept', 'application/pdf')
    };
  }
}
