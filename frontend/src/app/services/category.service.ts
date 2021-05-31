import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../dtos/product';
import {Category} from '../dtos/category';
import {Pagination} from '../dtos/pagination';

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
    return this.httpClient.get<Category[]>(this.messageBaseUri +'/all');
  }

  /**
   * Loads all categories from the backend in a paginated manner specified by page and pageSize
   */
  getCategoriesPerPage(page: number, pageCount): Observable<Pagination<Category>> {
    return this.httpClient.get<Pagination<Category>>(this.messageBaseUri + '/?page='+ page + '&page_count='+pageCount);
  }

  /**
   * adds a new category to the backend
   */
  addCategory(category: Category): Observable<Category> {
    return this.httpClient.post<Category>(this.messageBaseUri, category);
  }
}
