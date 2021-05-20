import {AuthRequest} from '../../dtos/auth-request';
import {Observable} from 'rxjs';

export interface IAuthService {
  setToken(authResponse: string): void;

  getTokenExpirationDate(token: string): Date;

  /**
   * Login in the user. If it was successful, a valid JWT token will be stored
   *
   * @param authRequest User data
   */
  loginUser(authRequest: AuthRequest): Observable<string>;

  /**
   * Check if a valid JWT token is saved in the localStorage
   */
  isLoggedIn(): void;

  logoutUser(): void;

  getToken(): string;

  /**
   * Returns the user role based on the current token
   */
  getUserRole(): string;
}
