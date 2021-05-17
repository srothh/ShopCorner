import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Customer} from '../dtos/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customerBaseUri: string = this.globals.backendUri + '/customers';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Register a new customer.
   *
   * @param customer The customer dto to register
   */
  addCustomer(customer: Customer): Observable<Customer> {
    console.log('Create new Customer', customer);
    return this.httpClient.post<Customer>(this.customerBaseUri , customer);
  }

}
