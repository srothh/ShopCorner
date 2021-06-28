import {Component, OnInit} from '@angular/core';
import {Category} from '../../../dtos/category';

@Component({
  selector: 'app-operator-add-category',
  templateUrl: './operator-add-category.component.html',
  styleUrls: ['./operator-add-category.component.scss']
})
export class OperatorAddCategoryComponent implements OnInit {
  errorOccurred: boolean;
  errorMessage: string;

  newCategory: Category;

  constructor() {
  }

  ngOnInit(): void {
  }

}
