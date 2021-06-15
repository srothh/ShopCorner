import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
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
    return this.httpClient.get<Category[]>(this.categoryBaseUri + '/all');
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
    const params = new HttpParams()
      .set(this.globals.requestParamKeys.pagination.page, String(page))
      .set(this.globals.requestParamKeys.pagination.pageCount, String(pageCount));

    return this.httpClient.get<Pagination<Category>>(this.categoryBaseUri, {
      params,
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
    return this.httpClient.post<Category>(this.categoryBaseUri, category, {
      headers: this.getHeadersForOperator()
    });
  }

  /**
   * updates an already existing category to the backend
   *
   * @param categoryId the Id of the category to execute the update
   * @param category the updated category
   * @return An Observable with no return value
   */
  updateCategory(categoryId: number, category: Category): Observable<void> {
    return this.httpClient.put<void>(this.categoryBaseUri + '/' + categoryId, category, {
      headers: this.getHeadersForOperator()
    });
  }
  /**
   * deletes a category in the backend
   *
   * @param categoryId the Id of the category to execute the delete action
   * @return An Observable with no return value
   */
  deleteCategory(categoryId: number): Observable<void> {
    return this.httpClient.delete<void>(this.categoryBaseUri + '/' + categoryId, {
      headers: this.getHeadersForOperator()
    });
  }
  /**
   * Gets a category specified by the id
   *
   * @param categoryId the Id of the category to retrieve from the database
   * @return An observable with the requested category
   */
  getCategoryById(categoryId: number) {
    return this.httpClient.get<Category>(this.categoryBaseUri + '/' + categoryId, {
      headers: this.getHeadersForOperator()
    });
  }

  private getHeadersForOperator(): HttpHeaders {
    return new HttpHeaders()
      .set('Authorization', `Bearer ${this.operatorAuthService.getToken()}`);
  }
}
