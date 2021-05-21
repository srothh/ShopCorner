import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../dtos/product';
import {Category} from '../dtos/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private messageBaseUri: string = this.globals.backendUri + '/categories';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all categories from the backend
   */
  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.messageBaseUri);
  }
}
