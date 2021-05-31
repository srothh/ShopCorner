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
    if (this.newCategory === undefined){
      this.newCategory = this.createDefaultCategory();
    }
    this.categoryForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]]});
  }
  resetState(){
    this.errorMessage = null;
    this.errorOccurred = undefined;
  }
  createDefaultCategory(){
    return new Category(null, null);
  }
  submitCategory(){
    this.newCategory.name = this.categoryForm.get('name').value;
    this.categoryService.addCategory(this.newCategory).subscribe((categoryData)=>{
      this.newCategory.id = categoryData.id;
      this.errorOccurred = false;
      this.router.navigate(['operator/categories']).then();
    },error => {
      this.errorOccurred = true;
      this.errorMessage = error.error.message;
    });
  }
  get categoryFormControl() {
    return this.categoryForm.controls;
  }
  whiteSpaceValidator(control: AbstractControl) {
    const isWhitespace = (control.value || '').trim().length < 3;
    const isValid = !isWhitespace;
    return isValid ? null : {whitespace: true};
  }

}
