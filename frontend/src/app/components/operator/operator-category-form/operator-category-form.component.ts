import { Component, OnInit, Input } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-operator-category-form',
  templateUrl: './operator-category-form.component.html',
  styleUrls: ['./operator-category-form.component.scss']
})
export class OperatorCategoryFormComponent implements OnInit {
  @Input()
  category: Category;
  errorOccurred: boolean;
  errorMessage: string;
  categoryForm: FormGroup;
  addCategoryEnabled: boolean;
  inEditMode: boolean;
  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    if (this.category === undefined && this.router.url.includes('add')){
      this.category = this.createDefaultCategory();
      this.addCategoryEnabled = true;
      this.inEditMode = false;
    } else {
      this.addCategoryEnabled = false;
      this.inEditMode = true;
    }
    this.categoryForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]]});
    if (this.addCategoryEnabled === false) {
      this.setFormProperties();
      this.categoryForm.disable();
    }
    console.log('addCategoryEnabled', this.addCategoryEnabled);
    console.log('EditMode', this.inEditMode);
  }
  setFormProperties(){
    this.categoryForm.controls['name'].setValue(this.category.name);
  }
  resetState(){
    this.errorMessage = null;
    this.errorOccurred = undefined;
  }
  createDefaultCategory(){
    return new Category(null, null);
  }
  submitCategory(){
    this.category.name = this.categoryForm.get('name').value;
    this.categoryService.addCategory(this.category).subscribe((categoryData)=>{
      this.category.id = categoryData.id;
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
  goBackToCategoriesOverview(){
    this.router.navigate(['operator/categories']).then();
  }

}
