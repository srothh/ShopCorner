import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
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
   *
   * @param id of the invoice
   * @return invoice
   */
  getInvoiceById(id: number): Observable<Invoice> {
    console.log('Load invoice by ' + id);
    return this.httpClient.get<Invoice>(this.invoiceBaseUri + '/' + id);
  }

  /**
   * Loads invoice pdf by id from the backend
   *
   * @param id of the invoice
   * @return pdf generated from the invoice entry
   */
  getInvoiceAsPdfById(id: number): Observable<any> {
    const httpOptions = {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders().set('Accept', 'application/pdf'),
    };
    return this.httpClient.get(this.invoiceBaseUri + '/getinvoicepdf/' + id, httpOptions);
  }

  /**
   * Creates a new invoice entry and a pdf in the backend.
   *
   * @param invoice to be created
   * @return invoice generated from the given invoice and invoice entry
   */
  createInvoice(invoice: Invoice): Observable<Invoice> {
    return this.httpClient.post<Invoice>(this.invoiceBaseUri, invoice);
  }


  /**
   * Creates a new invoice entry and a pdf in the backend.
   *
   * @param invoice to be created
   * @return pdf generated from the given invoice and invoice entry
   */
  createInvoiceAsPdf(invoice: Invoice): Observable<any> {
    const httpOptions = {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders().set('Accept', 'application/pdf'),
    };
    return this.httpClient.post(this.invoiceBaseUri + '/createinvoicepdf', invoice , httpOptions);
  }
}
