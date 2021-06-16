import { Injectable } from '@angular/core';
import {Operator} from '../dtos/operator';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Permissions} from '../dtos/permissions.enum';
import {Globals} from '../global/globals';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Pagination} from '../dtos/pagination';


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
    return this.httpClient.get<Operator>(this.operatorBaseUri + '/' + loginName, {headers: this.getHeadersForOperator()});
  }


  /**
   * Creates a new operator account in the backend.
   *
   * @param operator to be created
   */
  createOperator(operator: Operator): Observable<Operator> {
    console.log('Create new operator account', operator);
    return this.httpClient.post<Operator>(
      this.operatorBaseUri,  operator
    , {headers: this.getHeadersForOperator()});
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
    , {headers: this.getHeadersForOperator()});
  }

  /**
   * Updates the specified operator's password in the backend.
   *
   * @param id of the operator whose password is to be updated
   * @param oldPassword the password to be updated
   * @param newPassword the new password
   */
  updatePassword(id: number, oldPassword: string, newPassword: string): Observable<string>{
    console.log('Update password of operator ', id);
    return this.httpClient.post<string>(
      this.operatorBaseUri + '/updatePassword/' + id, {oldPassword, newPassword}
      , {headers: this.getHeadersForOperator()});
  }

  /**
   * fetches all operator accounts from backend
   *
   * @param page that is needed
   * @param pageCount amount of operators per page
   * @param permissions of needed operators
   */
  getOperatorsPage(page: number, pageCount: number, permissions: Permissions): Observable<Pagination<Operator>> {
    console.log('Get Operators with permission: ', permissions, ' for page: ', page);
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount))
      .set(this.globals.requestParamKeys.operators.permissions, String(permissions));

    return this.httpClient.get<Pagination<Operator>>(this.operatorBaseUri, {params, headers: this.getHeadersForOperator()});
  }

  /**
   * fetches count of Operators from backend
   */
  getOperatorCount(): Observable<number[]> {
    console.log('Get count of Operators');
    return this.httpClient.get<number[]>(this.operatorBaseUri + '/count', {headers: this.getHeadersForOperator()});
  }

  /**
   * sends delete request with id to backend
   *
   * @param id of operator that should be deleted
   */
  deleteOperator(id: number): Observable<void> {
    console.log('Delete operator with id: ' + id);
    return this.httpClient.delete<void>(this.operatorBaseUri + '/' + id, {headers: this.getHeadersForOperator()});
  }

  /**
   * sends a patch request with id and the new permissions to the backend
   *
   * @param operator whose permissions should be changed
   */
  changePermissions(operator: Operator): Observable<void> {
    console.log('Change permissions of operator with id ' + operator.id);
    if(operator.permissions === Permissions.admin){
      return this.httpClient.patch<void>(this.operatorBaseUri + '/' + operator.id, {permissions: 'employee'},
        {headers: this.getHeadersForOperator()});
    } else {
      return this.httpClient.patch<void>(this.operatorBaseUri + '/' + operator.id, {permissions: 'admin'},
        {headers: this.getHeadersForOperator()});
    }
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }

}
