import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {CustomerAuthService} from './auth/customer-auth.service';
import {Observable} from 'rxjs';
import {Customer} from '../dtos/customer';


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
    return this.httpClient.get<Customer>(this.meBaseUri, {headers: this.getHeadersForCustomer()});
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
   * Deletes the current customer's profile data
   */
  deleteMyAccount(): Observable<void> {
    return this.httpClient.delete<void>(this.meBaseUri, {headers: this.getHeadersForCustomer()});
  }

  private getHeadersForCustomer(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`);
  }
}
