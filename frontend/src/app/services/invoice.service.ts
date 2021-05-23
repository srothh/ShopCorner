import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Invoice} from '../dtos/invoice';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private invoiceBaseUri: string = this.globals.backendUri + '/invoice';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all invoices from the backend
   */
  getInvoice(): Observable<Invoice[]> {
    return this.httpClient.get<Invoice[]>(this.invoiceBaseUri);
  }

  /**
   * Loads invoice by id from the backend
   */
  getInvoiceById(id: number): Observable<Invoice> {
    console.log('Load invoice by ' + id);
    return this.httpClient.get<Invoice>(this.invoiceBaseUri + '/' + id);
  }

  /**
   * Loads invoice by id from the backend
   */
  getInvoiceAsPdf(id: number): Observable<string> {
    console.log('Load invoice by ' + id);
    return this.httpClient.get<string>(this.invoiceBaseUri + '/getinvoicepdf/' + -1);
  }

  createInvoice(invoice: Invoice): Observable<Invoice> {
    return this.httpClient.post<Invoice>(this.invoiceBaseUri, invoice);
  }
}
