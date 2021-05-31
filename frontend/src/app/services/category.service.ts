import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Product} from '../dtos/product';
import {Category} from '../dtos/category';
import {Pagination} from '../dtos/pagination';
import {OperatorAuthService} from './auth/operator-auth.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private categoryBaseUri: string = this.globals.backendUri + '/categories';

  constructor(private httpClient: HttpClient, private globals: Globals, private operatorAuthService: OperatorAuthService) {
  }

  /**
   * Loads all categories from the backend
   */
  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.categoryBaseUri +'/all');
  }

  /**
   * Loads all categories from the backend in a paginated manner specified by page and pageSize
   *
   * @param page the current page the user is in
   * @param pageCount number of all entries in a page
   *
   * @return An Observable with paginated category
   */
  getCategoriesPerPage(page: number, pageCount): Observable<Pagination<Category>> {
    return this.httpClient.get<Pagination<Category>>(this.categoryBaseUri + '/?page='+ page + '&page_count='+pageCount, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * adds a new category to the backend
   *
   * @param category the new category to be added
   * @return An Observable with the newly added category
   */
  addCategory(category: Category): Observable<Category> {
    return this.httpClient.post<Category>(this.categoryBaseUri, category,{
      headers: this.getHeadersForOperator()
    });
  }
  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }
}
