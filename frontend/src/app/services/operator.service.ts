import { Injectable } from '@angular/core';
import {Operator} from '../dtos/operator';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

const baseUri = 'http://localhost:8080/api/v1/operators';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  constructor(private httpClient: HttpClient) {
  }


  /**
   * Creates a new operator account in the backend.
   *
   * @param operator to be created
   */
  createOperator(operator: Operator): Observable<Operator> {
    console.log('Create new operator account', operator);
    return this.httpClient.post<Operator>(
      baseUri + '/register',  operator
    );
  }

  /**
   * Updates the specified operator in the backend.
   *
   * @param operator to be updated
   */
  updateOperator(operator: Operator): Observable<any> {
    console.log('Update operator', operator);
    return this.httpClient.put<Operator>(
      baseUri + '/' + operator.id,  operator
    );
  }

}
