import {Component, OnInit} from '@angular/core';
import {Category} from '../../../dtos/category';
import {ActivatedRoute, Router} from '@angular/router';
import {CategoryService} from '../../../services/category/category.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';
import {faEdit, faMinusCircle} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgdbModalActionComponent} from '../../common/ngbd-modal-action/ngdb-modal-action.component';

@Component({
  selector: 'app-operator-category-details',
  templateUrl: './operator-category-details.component.html',
  styleUrls: ['./operator-category-details.component.scss']
})
export class OperatorCategoryDetailsComponent implements OnInit {
  errorOccurred: boolean;
  errorMessage: string;
  selectedCategory: Category;
  faMinusCircle = faMinusCircle;
  faEdit = faEdit;

  constructor(private router: Router,
              private categoryService: CategoryService,
              private authService: OperatorAuthService,
              private activatedRouter: ActivatedRoute,
              private modalService: NgbModal) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.selectedCategory = this.router.getCurrentNavigation().extras.state[0] as Category;
    }
  }

  attemptToDeleteCategory() {
    const modalRef = this.modalService.open(NgdbModalActionComponent);
    modalRef.componentInstance.title = 'Warnung';
    modalRef.componentInstance.body = 'Wollen Sie diese Kategorie löschen?';
    modalRef.componentInstance.actionButtonTitle = 'Löschen';
    modalRef.componentInstance.actionButtonStyle = 'danger';
    modalRef.componentInstance.action = () => {
      this.deleteCategory();
    };
  }

  ngOnInit(): void {
  }

  deleteCategory() {
    let categoryId;
    if (this.selectedCategory === undefined) {
      categoryId = +this.activatedRouter.snapshot.paramMap.get('id');
    } else {
      categoryId = this.selectedCategory.id;
    }
    this.categoryService.deleteCategory(categoryId).subscribe(() => {
      this.router.navigate(['operator/categories']).then();
    }, error => {
      this.errorMessage = error;
      this.errorOccurred = true;
    });
  }

  getPermission(): string {
    return this.authService.getUserRole();
  }

}
