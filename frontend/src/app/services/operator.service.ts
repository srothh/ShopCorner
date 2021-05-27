import { Injectable } from '@angular/core';
import {Operator} from '../dtos/operator';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Permissions} from '../dtos/permissions.enum';
import {Globals} from '../global/globals';
import {OperatorAuthService} from './auth/operator-auth.service';


@Injectable({
  providedIn: 'root'
})
export class OperatorService {
  private operatorBaseUri: string = this.globals.backendUri + '/operators';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Loads the specified operator from the backend
   *
   * @param loginName of operator to load
   */
  getOperatorByLoginName(loginName: string): Observable<Operator> {
    console.log('Load operator ' + loginName);
    return this.httpClient.get<Operator>(this.operatorBaseUri + '/' + loginName, {headers: this.getHeaders()});
  }


  /**
   * Creates a new operator account in the backend.
   *
   * @param operator to be created
   */
  createOperator(operator: Operator): Observable<Operator> {
    console.log('Create new operator account', operator);
    return this.httpClient.post<Operator>(
      this.operatorBaseUri + '/register',  operator
    , {headers: this.getHeaders()});
  }

  /**
   * Updates the specified operator in the backend.
   *
   * @param operator to be updated
   */
  updateOperator(operator: Operator): Observable<any> {
    console.log('Update operator', operator);
    return this.httpClient.put<Operator>(
      this.operatorBaseUri + '/' + operator.id,  operator
    , {headers: this.getHeaders()});
  }

  /**
   * fetches all operator accounts from backend
   */
  getOperatorsPage(page: number, pageCount: number, permissions: Permissions): Observable<Operator[]> {
    console.log('Get Operators with permission: ', permissions, ' for page: ', page);
    return this.httpClient.get<Operator[]>(this.operatorBaseUri + '?page=' + page + '&page_count=' + pageCount +
      '&permissions=' + permissions, {headers: this.getHeaders()});
  }

  /**
   * fetches count of Operators from backend
   */
  getOperatorCount(): Observable<number[]> {
    console.log('Get count of Operators');
    return this.httpClient.get<number[]>(this.operatorBaseUri, {headers: this.getHeaders()});
  }


  private getHeaders(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

}
