import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Observable} from 'rxjs';
import {Pagination} from '../dtos/pagination';
import {Promotion} from '../dtos/promotion';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  private promotionBaseUri: string = this.globals.backendUri + '/promotions';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Register a new customer.
   *
   * @param promotion The promotion dto to register
   * @return The promotion dto as received from the backend
   */
  addPromotion(promotion: Promotion): Observable<Promotion> {
    console.log('Create new Promotion', promotion);
    return this.httpClient.post<Promotion>(this.promotionBaseUri, promotion, {headers: this.getHeadersForOperator()});
  }

  /**
   * Retrieve a page of promotions from the backend.
   *
   * @param page the number of the page to fetch
   * @param pageCount the size of the page to be fetched
   * @return The promotions retrieved from the backend
   */
  getAllPromotionsForPage(page: number, pageCount: number): Observable<Pagination<Promotion>> {
    console.log('Get promotions for page', page);
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount));

    return this.httpClient.get<Pagination<Promotion>>(this.promotionBaseUri, {params, headers: this.getHeadersForOperator()});
  }

  getPromotionByCode(code: string) {
    return this.httpClient.get<Promotion>(this.promotionBaseUri + '/' + code);
  }


  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

}
