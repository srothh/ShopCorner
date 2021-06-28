import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../../global/globals';
import {OperatorAuthService} from '../auth/operator-auth.service';
import {Observable} from 'rxjs';
import {ShopSettings} from '../../dtos/shop-settings';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private shopBaseUri: string = this.globals.backendUri + '/shop';
  private shopSettingsKey = 'shopSettings';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  static shopSettingsMapper(s: ShopSettings) {
    return new ShopSettings(
      s.title, s.logo,
      s.bannerTitle, s.bannerText,
      s.street, s.houseNumber,
      s.stairNumber, s.doorNumber,
      s.postalCode, s.city,
      s.phoneNumber, s.email
    );
  }

  /**
   * Updates the shops settings in the backend
   *
   * @param shopSettings the settings that will override the old
   * @return shopSettings current settings after update
   */
  updateSettings(shopSettings: ShopSettings): Observable<ShopSettings> {
    console.log('Update settings');
    return this.httpClient.put<ShopSettings>(this.shopBaseUri + '/settings', shopSettings, {headers: this.getHeadersForOperator()})
      .pipe(
        tap(() => {
          this.setSettings(shopSettings);
        }),
        map(ShopService.shopSettingsMapper),
      );
  }

  /**
   * Fetches settings from the backend and caches them
   *
   * @return shopSettings current settings
   */
  loadSettings(): Observable<ShopSettings> {
    console.log('Load settings from backend into cache');
    return this.httpClient.get<ShopSettings>(this.shopBaseUri + '/settings', {headers: this.getHeadersForOperator()})
      .pipe(
        tap((shopSettings) => {
          this.setSettings(shopSettings);
        }),
        map(ShopService.shopSettingsMapper)
      );
  }

  /**
   * Gets settings from the cache
   */
  getSettings(): ShopSettings {
    return JSON.parse(localStorage.getItem(this.shopSettingsKey));
  }

  private setSettings(shopSettings: ShopSettings) {
    localStorage.setItem(this.shopSettingsKey, JSON.stringify(shopSettings));
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }
}
