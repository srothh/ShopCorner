import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Operator} from '../dtos/operator';
import {Permissions} from '../dtos/permissions.enum';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {
  private operatorBaseUri: string = this.globals.backendUri + '/operators';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * fetches all operator accounts from backend
   */
  getOperatorsPage(page: number, permissions: Permissions): Observable<Operator[]> {
    console.log('Get Operators with permission: ', permissions, ' for page: ', page);
    return this.httpClient.get<Operator[]>(this.operatorBaseUri + '?page=' + page + '&permissions=' + permissions);
  }

  /**
   * fetches count of Operators from backend
   */
  getOperatorCount(): Observable<number[]> {
    console.log('Get count of Operators');
    return this.httpClient.get<number[]>(this.operatorBaseUri);
  }
}
