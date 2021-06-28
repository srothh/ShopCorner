import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {AuthRequest} from '../../dtos/auth-request';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import jwt_decode from 'jwt-decode';
import {IAuthService} from './interface-auth.service';

@Injectable({
  providedIn: 'root'
})
export class OperatorAuthService implements IAuthService {

  private authBaseUri: string = this.globals.backendUri + '/authentication/operators';
  private authTokenKey = 'operatorAuthToken';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Login in the user. If it was successful, a valid JWT token will be stored
   *
   * @param authRequest User data
   */
  loginUser(authRequest: AuthRequest): Observable<string> {
    return this.httpClient.post(this.authBaseUri, authRequest, {responseType: 'text'})
      .pipe(
        tap((authResponse: string) => this.setToken(authResponse))
      );
  }


  /**
   * Check if a valid JWT token is saved in the localStorage
   */
  isLoggedIn() {
    return !!this.getToken() && (this.getTokenExpirationDate(this.getToken()).valueOf() > new Date().valueOf());
  }

  logoutUser() {
    console.log('Logout');
    localStorage.removeItem(this.authTokenKey);
  }

  getToken() {
    return localStorage.getItem(this.authTokenKey);
  }

  /**
   * Returns the username of the logged in user based on the current token
   */
  getUser() {
    if (this.getToken() != null) {
      const decoded: any = jwt_decode(this.getToken());
      return decoded.sub;
    }
  }

  /**
   * Returns the user role based on the current token
   */
  getUserRole() {
    if (this.getToken() != null) {
      const decoded: any = jwt_decode(this.getToken());
      const authInfo: string[] = decoded.rol;
      if (authInfo.includes('ROLE_ADMIN')) {
        return 'ADMIN';
      }
      if (authInfo.includes('ROLE_EMPLOYEE')) {
        return 'EMPLOYEE';
      }
    }
    return 'UNDEFINED';
  }

  setToken(authResponse: string) {
    localStorage.setItem(this.authTokenKey, authResponse);
  }

  getTokenExpirationDate(token: string): Date {

    const decoded: any = jwt_decode(token);
    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

}
