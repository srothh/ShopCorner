import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Invoice} from '../dtos/invoice';
import {Product} from '../dtos/product';
import {OperatorAuthService} from './auth/operator-auth.service';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private invoiceBaseUri: string = this.globals.backendUri + '/invoices';
  private productBaseUri: string = this.globals.backendUri + '/products';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Loads all invoices from the backend
   *
   * @return invoiceList with all invoices
   */
  getInvoice(): Observable<Invoice[]> {
    return this.httpClient.get<Invoice[]>(this.invoiceBaseUri, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * Loads all simple products from the backend
   *
   * @return simpleProductsList
   */
  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.productBaseUri + '/simple', {
      headers: this.getHeadersForOperator()
    });
  }


  /**
   * Loads invoice by id from the backend
   *
   * @param id of the invoice
   * @return invoice
   */
  getInvoiceById(id: number): Observable<Invoice> {
    return this.httpClient.get<Invoice>(this.invoiceBaseUri + '/' + id, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * Loads invoice pdf by id from the backend
   *
   * @param id of the invoice
   * @return pdf generated from the invoice entry
   */
  getInvoiceAsPdfById(id: number): Observable<any> {
    return this.httpClient.get(this.invoiceBaseUri + '/' + id + '/pdf', this.getPdfHeadersForOperator());
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

    return this.httpClient.post(this.invoiceBaseUri, invoice , this.getPdfHeadersForOperator());
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

  private getPdfHeadersForOperator(): any {
    const httpOptions = {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`).set('Accept', 'application/pdf')
    };
    return httpOptions;
  }
}
