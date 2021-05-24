import { Injectable } from '@angular/core';
import {Operator} from '../dtos/operator';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Permissions} from '../dtos/permissions.enum';
import {Globals} from '../global/globals';


@Injectable({
  providedIn: 'root'
})
export class OperatorService {
  private operatorBaseUri: string = this.globals.backendUri + '/operators';

  constructor(private httpClient: HttpClient, private globals: Globals) {
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
    );
  }

  /**
   * fetches all operator accounts from backend
   *
   * @param page that is needed
   * @param pageCount amount of operators per page
   * @param permissions of needed operators
   */
  getOperatorsPage(page: number, pageCount: number, permissions: Permissions): Observable<Operator[]> {
    console.log('Get Operators with permission: ', permissions, ' for page: ', page);
    return this.httpClient.get<Operator[]>(this.operatorBaseUri + '?page=' + page + '&page_count=' + pageCount +
      '&permissions=' + permissions);
  }

  /**
   * fetches count of Operators from backend
   */
  getOperatorCount(): Observable<number[]> {
    console.log('Get count of Operators');
    return this.httpClient.get<number[]>(this.operatorBaseUri);
  }

  /**
   * sends delete request with id to backend
   *
   * @param id of operator that should be deleted
   */
  deleteOperator(id: number): Observable<void> {
    console.log('Delete operator with id: ' + id);
    return this.httpClient.delete<void>(this.operatorBaseUri + '/' + id);
  }
}
