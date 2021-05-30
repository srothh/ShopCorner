import { Component, OnInit } from '@angular/core';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category.service';

@Component({
  selector: 'app-operator-categories',
  templateUrl: './operator-categories.component.html',
  styleUrls: ['./operator-categories.component.scss']
})
export class OperatorCategoriesComponent implements OnInit {
  categories: Category[];
  errorOccurred: boolean;
  errorMessage: string;
  page: number;
  pageSize: number;
  collectionSize: number;
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.fetchCategories();
  }
  fetchCategories(){
    this.categoryService.getCategories().subscribe((categoriesData)=>{
      this.categories = categoriesData;
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
  goToCategoryDetails(category: Category, event){}
  clickedCheckMark(event, index: number) {}
  previousPage() {}
  nextPage() {}
  addNewCategory(){}

}
