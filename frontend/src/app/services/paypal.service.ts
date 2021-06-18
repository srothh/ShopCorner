import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Order} from '../dtos/order';
import {Observable} from 'rxjs';
import {CustomerAuthService} from './auth/customer-auth.service';
import {ConfirmedPayment} from '../dtos/confirmedPayment';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {
  private paypalBaseURI: string = this.globals.backendUri + '/paypal';

  constructor(private httpClient: HttpClient, private globals: Globals, private customerAuthService: CustomerAuthService) {
  }

  /** Initiates a new payment
   *
   * @param order dto containing information on the order
   * @return the the url redirecting to the paypal service
   */
  createPayment(order: Order): Observable<string> {
    const options  = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`),
      responseType: 'text' as 'text'
    };
    return this.httpClient.post(this.paypalBaseURI, order ,options);
  }
  /** Confirm the previously created new payment
   *
   * @param confirmedPayment the confirmedPayment containing payerId and paymentId to finalise the payment
   *
   * @return the text with either 'successful payment' or 'not successful payment'
   */
  confirmPayment(confirmedPayment: ConfirmedPayment): Observable<string> {
    const options  = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`),
      responseType: 'text' as 'text'
    };
    return this.httpClient.post(this.paypalBaseURI + '/confirmation',confirmedPayment, options);
  }

}
