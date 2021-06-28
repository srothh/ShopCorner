import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {TaxRate} from '../dtos/tax-rate';

@Injectable({
  providedIn: 'root'
})
export class TaxRateService {
  private messageBaseUri: string = this.globals.backendUri + '/tax-rates';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all tax-rates from the backend
   *
   * @return all taxrates
   */
  getTaxRates(): Observable<TaxRate[]> {
    return this.httpClient.get<TaxRate[]>(this.messageBaseUri);
  }
}
