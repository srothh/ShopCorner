import {AuthRequest} from '../../dtos/auth-request';
import {Observable} from 'rxjs';

export interface IAuthService {
  /**
   * Saves the token locally, usually in localStorage
   *
   * @param authResponse the authResponse from requesting an access token
   */
  setToken(authResponse: string): void;

  /**
   * Gets the expiration date
   *
   * @param token the saved access token
   */
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

  /**
   * Logs out the user
   */
  logoutUser(): void;

  /**
   * Gets the current saved token
   */
  getToken(): string;

  /**
   * Returns the user role based on the current token
   */
  getUserRole(): string;
}
