import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {Operator} from '../dtos/operator';

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
  getAllOperators(): Observable<Operator[]> {
    console.log('Get all Operators');
    return this.httpClient.get<Operator[]>(this.operatorBaseUri);
  }
}
