import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {Observable} from 'rxjs';
import {Invoice} from '../../dtos/invoice';
import {Product} from '../../dtos/product';
import {OperatorAuthService} from '../auth/operator-auth.service';
import {Pagination} from '../../dtos/pagination';
import {InvoiceType} from '../../dtos/invoiceType.enum';
import {Customer} from '../../dtos/customer';
import {Order} from '../../dtos/order';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private invoiceBaseUri: string = this.globals.backendUri + '/invoices';
  private productBaseUri: string = this.globals.backendUri + '/products';
  private customerBaseUri: string = this.globals.backendUri + '/customers';
  private orderBaseUri: string = this.globals.backendUri + '/orders';

  constructor(private httpClient: HttpClient,
              private globals: Globals,
              private operatorAuthService: OperatorAuthService
  ) {
  }

  /**
   * Retrieve a page of invoice from the backend.
   *
   * @param page the number of the page to fetch
   * @param pageCount the size of the page to be fetched
   * @param type the invoiceType of the invoices
   * @return The invoice retrieved from the backend
   */
  getAllInvoicesForPage(page: number, pageCount: number, type: InvoiceType): Observable<Pagination<Invoice>> {
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount))
      .set(this.globals.requestParamKeys.invoice.invoiceType, String(type));
    return this.httpClient.get<Pagination<Invoice>>(this.invoiceBaseUri, {params, headers: this.getHeadersForOperator()});
  }

  /**
   * Retrieves all invoices for given time frame.
   *
   * @param start of time period
   * @param end of time period
   * @return invoiceList with all invoices in time period
   */
  getAllInvoicesByDate(start: Date, end: Date): Observable<Invoice[]> {
    console.log('Get invoices from {} to {}', start.toISOString().split('T')[0], end.toISOString().split('T')[0]);
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.date.start, String(start.toISOString().split('T')[0]))
      .set(this.globals.requestParamKeys.date.end, String(end.toISOString().split('T')[0]));
    return this.httpClient.get<Invoice[]>(this.invoiceBaseUri + '/stats', {params, headers: this.getHeadersForOperator()});
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
   * Loads customer by invoiceId from the backend
   *
   * @param id of the invoice
   * @return customer from invoice
   */
  getCustomerById(id: number): Observable<Customer> {
    return this.httpClient.get<Customer>(this.customerBaseUri + '/' + id, {
      headers: this.getHeadersForOperator()
    });
  }



  /**
   * Loads invoice by id from the backend
   *
   * @param id of the invoice
   * @return invoice with id
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
   * Set invoice entry to canceled.
   *
   * @param invoice to be updated
   * @return invoice updated from the given invoice and invoice entry
   */
  setInvoiceCanceled(invoice: Invoice): Observable<Invoice> {
    console.log(invoice);
    return this.httpClient.patch<Invoice>(this.invoiceBaseUri + '/' + invoice.id, invoice, {headers: this.getHeadersForOperator()});
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

  /**
   * Loads customer by orderNumber from the backend
   *
   * @param orderNumber of the invoice
   * @return order from invoice
   */
  getOrderByOrderNumber(orderNumber: string): Observable<Order> {
    return this.httpClient.get<Order>(this.orderBaseUri + '/' + orderNumber + '/order', {headers: this.getHeadersForOperator()});
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

  private getPdfHeadersForOperator(): any {
    return {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`).set('Accept', 'application/pdf')
    };
  }
}
