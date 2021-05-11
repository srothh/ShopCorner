import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Customer} from '../dtos/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private addressBaseUri: string = this.globals.backendUri + '/users';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  addCustomer(customer: Customer): Observable<Customer> {
    console.log('Create new Customer', customer);
    return this.httpClient.post<Customer>(this.addressBaseUri, customer);
  }

}
