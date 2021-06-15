import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category.service';
import {Router, UrlSerializer} from '@angular/router';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {faEdit, faMinusCircle, faPlusCircle} from '@fortawesome/free-solid-svg-icons';
import {NgdbModalActionComponent} from '../../common/ngbd-modal-action/ngdb-modal-action.component';


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
  faPlusCircle = faPlusCircle;
  faMinusCircle = faMinusCircle;
  constructor(private categoryService: CategoryService,
              private router: Router,
              private urlSerializer: UrlSerializer,
              private authService: OperatorAuthService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fetchCategories();
  }
  attemptToDeleteMultipleCategories(){
    const modalRef = this.modalService.open(NgdbModalActionComponent);
    modalRef.componentInstance.title = 'Warnung';
    modalRef.componentInstance.body = `Wollen Sie ${this.selectedCategories.length} Kategorie(n) löschen?`;
    modalRef.componentInstance.actionButtonTitle = 'Löschen';
    modalRef.componentInstance.actionButtonStyle = 'danger';
    modalRef.componentInstance.action = () => {
      this.deleteCategories();
    };
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
  deleteCategories(){
    for (const selectedCategory of this.selectedCategories) {
      this.categoryService.deleteCategory(selectedCategory.id).subscribe(() => {
        if (this.selectedCategories.indexOf(selectedCategory) === this.selectedCategories.length - 1) {
          if ((this.page + 1) * this.pageSize >= this.collectionSize &&
            // categories per page equals selected products -> return to previous page
            this.categories.length === this.selectedCategories.length &&
            this.page > 0) {
            this.previousPage();
          } else {
            this.fetchCategories();
          }
          this.collectionSize -= this.selectedCategories.length;
          this.uncheckSelectedCategories();
        }
      }, error => {
        this.errorOccurred = true;
        this.errorMessage = error.error.message;
      });
    }
  }
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
