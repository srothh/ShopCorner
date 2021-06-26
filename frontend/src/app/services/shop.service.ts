import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {OperatorAuthService} from './auth/operator-auth.service';
import {Observable} from 'rxjs';
import {Operator} from '../dtos/operator';
import {ShopSettings} from '../dtos/shop-settings';
import {tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private shopBaseUri: string = this.globals.backendUri + '/shop';
  private shopSettingsKey = 'shopSettings';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) { }

  updateSettings(shopSettings: ShopSettings): Observable<ShopSettings> {
    console.log('Update settings');
    return this.httpClient.put<ShopSettings>(this.shopBaseUri + '/settings', shopSettings, {headers: this.getHeadersForOperator()})
      .pipe(
        tap(() => {
          this.setSettings(shopSettings);
        })
      );
  }

  loadSettings(): Observable<ShopSettings> {
    console.log('Get settings');
    return this.httpClient.get<ShopSettings>(this.shopBaseUri + '/settings', {headers: this.getHeadersForOperator()})
      .pipe(
        tap((shopSettings) => {
          this.setSettings(shopSettings);
        })
    );
  }

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
