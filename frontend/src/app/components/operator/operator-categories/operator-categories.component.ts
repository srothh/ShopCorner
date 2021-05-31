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
  page = 0;
  pageSize = 10;
  collectionSize = 0;
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.fetchCategories();
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
  goToCategoryDetails(category: Category, event){}
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
  addNewCategory(){}

}
