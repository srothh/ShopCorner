import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Order} from '../dtos/order';
import {Observable} from 'rxjs';
import {CustomerAuthService} from './auth/customer-auth.service';

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

  confirmPayment(payerId: string, paymentId: string): Observable<string> {
    const options  = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.customerAuthService.getToken()}`),
      responseType: 'text' as 'text',
    };
    return this.httpClient.post(this.paypalBaseURI + '/confirm' + '?paymentId='+paymentId+'&PayerID='+ payerId,null, options);
  }

}
