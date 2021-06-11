import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
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
  @ViewChildren('checkboxes') checkboxes: QueryList<ElementRef>;
  categories: Category[];
  errorOccurred: boolean;
  errorMessage: string;
  page = 0;
  pageSize = 10;
  collectionSize = 0;
  selectedCategories: Category[] = [];
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
    const targetHTMLElement = event.target.toString();
    if (!(targetHTMLElement.includes('HTMLLabelElement') || targetHTMLElement.includes('HTMLInputLabel'))) {
      this.router.navigate(['operator/categories/' + selectedCategory.id],{ state: [selectedCategory]}).then();
    }
  }

  clickedCheckMark(event, index: number) {
    event.stopPropagation();
    if (event.target.checked) {
      this.selectedCategories.push(this.categories[index]);
    } else {
      const category = this.categories[index];
      const deleteIndex = this.selectedCategories.indexOf(category);
      this.selectedCategories.splice(deleteIndex, 1);
    }
  }
  uncheckSelectedCategories(){
    this.checkboxes.forEach((element) => {
      element.nativeElement.checked = false;
    });
    this.selectedCategories = [];
  }
  deleteCategories(){}
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
