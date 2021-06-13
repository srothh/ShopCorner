import { Component, OnInit } from '@angular/core';
import {Category} from '../../../dtos/category';
import {Router} from '@angular/router';
import {CategoryService} from '../../../services/category.service';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

@Component({
  selector: 'app-operator-category-details',
  templateUrl: './operator-category-details.component.html',
  styleUrls: ['./operator-category-details.component.scss']
})
export class OperatorCategoryDetailsComponent implements OnInit {
  errorOccurred: boolean;
  errorMessage: string;
  selectedCategory: Category;
  constructor(private router: Router, private categoryService: CategoryService, private authService: OperatorAuthService) {
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.selectedCategory = this.router.getCurrentNavigation().extras.state[0] as Category;
    }
  }
  ngOnInit(): void {
  }
  deleteCategory(){
    this.categoryService.deleteCategory(this.selectedCategory.id).subscribe(() => {
      this.router.navigate(['operator/categories']).then();
    }, error => {
      this.errorMessage = error.error.message;
      this.errorOccurred = true;
    });
  }
  getPermission(): string {
    return this.authService.getUserRole();
  }

}
