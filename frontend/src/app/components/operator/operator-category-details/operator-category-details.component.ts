import { Component, OnInit } from '@angular/core';
import {Category} from '../../../dtos/category';
import {Router} from '@angular/router';

@Component({
  selector: 'app-operator-category-details',
  templateUrl: './operator-category-details.component.html',
  styleUrls: ['./operator-category-details.component.scss']
})
export class OperatorCategoryDetailsComponent implements OnInit {
  errorOccurred: boolean;
  errorMessage: string;
  selectedCategory: Category;
  constructor(private router: Router) {
    console.log('router.getCurrentNavigation().extras.state:', router.getCurrentNavigation().extras.state);
    if (router.getCurrentNavigation().extras.state !== undefined) {
      this.selectedCategory = this.router.getCurrentNavigation().extras.state[0] as Category;
      console.log('this.selectedCategory: ', this.selectedCategory);
    }
  }

  ngOnInit(): void {
  }
  deleteCategory(){
    console.log('');
  }
  getPermission(){
    console.log('');
  }

}
