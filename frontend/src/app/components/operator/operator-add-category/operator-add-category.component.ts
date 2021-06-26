import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-operator-add-category',
  templateUrl: './operator-add-category.component.html',
  styleUrls: ['./operator-add-category.component.scss']
})
export class OperatorAddCategoryComponent implements OnInit {
  errorOccurred: boolean;
  errorMessage: string;
  categoryForm: FormGroup;
  newCategory: Category;
  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
  }

}
