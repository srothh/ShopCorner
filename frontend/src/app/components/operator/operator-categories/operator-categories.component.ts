import { Component, OnInit } from '@angular/core';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category.service';
import {Router, UrlSerializer} from '@angular/router';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-categories',
  templateUrl: './operator-categories.component.html',
  styleUrls: ['./operator-categories.component.scss']
})
export class OperatorCategoriesComponent implements OnInit {
  categories: Category[];
  errorOccurred: boolean;
  errorMessage: string;
  page = 0;
  pageSize = 10;
  collectionSize = 0;
  constructor(private categoryService: CategoryService, private router: Router, private urlSerializer: UrlSerializer,
              private authService: OperatorAuthService) { }

  ngOnInit(): void {
    this.fetchCategories();
  }

  /**
   * calls on authentication service to return permission of logged in operator
   *
   * @return string role of logged in operator
   */
  getPermission(): string {
    return this.authService.getUserRole();
  }

  fetchCategories(){
    this.categoryService.getCategoriesPerPage(this.page, this.pageSize).subscribe((categoriesData)=>{
      this.categories = categoriesData.items;
      this.collectionSize = categoriesData.totalItemCount;
      this.errorOccurred = false;
    }, error => {
      this.errorOccurred = true;
      this.errorMessage = error.error.message;
    });
  }

  resetState(){
    this.errorOccurred = undefined;
    this.errorMessage = undefined;
  }

  goToCategoryDetails(selectedCategory: Category, event){
    console.log('selectedCategory', selectedCategory);
    this.router.navigate(['operator/categories/' + selectedCategory.id],{ state: [selectedCategory]}).then();
  }

  clickedCheckMark(event, index: number) {}

  previousPage() {
    if (this.page > 0) {
      this.page -= 1;
      this.fetchCategories();
    }
  }

  nextPage() {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.fetchCategories();
    }
  }

  addNewCategory(){
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addCategoryURL = currentURL.replace('categories', 'categories/add');
    this.router.navigate([addCategoryURL]).then();
  }
}
