import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../dtos/category';
import {CategoryService} from '../../../services/category/category.service';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {OperatorAuthService} from '../../../services/auth/operator-auth.service';

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
  faEdit = faEdit;

  constructor(private formBuilder: FormBuilder,
              private categoryService: CategoryService,
              private router: Router,
              private activatedRouter: ActivatedRoute,
              private authService: OperatorAuthService) {
  }

  ngOnInit(): void {
    if (this.category === undefined && this.router.url.includes('add')) {
      this.category = this.createDefaultCategory();
      this.addCategoryEnabled = true;
      this.inEditMode = false;
    } else {
      this.addCategoryEnabled = false;
      this.inEditMode = true;
    }
    this.categoryForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), this.whiteSpaceValidator]]
    });
    if (this.addCategoryEnabled === false) {
      this.setFormProperties();
      this.categoryForm.disable();
    }
  }

  setFormProperties() {
    if (this.category === undefined) {
      const categoryId = +this.activatedRouter.snapshot.paramMap.get('id');
      this.fetchSelectedCategory(categoryId);
    } else {
      this.categoryForm.controls['name'].setValue(this.category.name);
    }
  }

  fetchSelectedCategory(categoryId: number) {
    this.categoryService.getCategoryById(categoryId).subscribe((categoryData) => {
      this.category = categoryData;
      this.setFormProperties();
    }, error => {
      this.errorOccurred = true;
      this.errorMessage = error;
    });
  }

  resetState() {
    this.errorMessage = null;
    this.errorOccurred = undefined;
  }

  createDefaultCategory() {
    return new Category(null, null);
  }

  submitCategory() {
    this.category.name = this.categoryForm.get('name').value.trim();
    if (this.router.url.includes('add')) {
      this.categoryService.addCategory(this.category).subscribe((categoryData) => {
        this.category.id = categoryData.id;
        this.errorOccurred = false;
        this.router.navigate(['operator/categories']).then();
      }, error => {
        this.errorOccurred = true;
        this.errorMessage = error;
      });
    } else {
      this.updateCategory();
    }
  }

  updateCategory() {
    this.categoryService.updateCategory(this.category.id, this.category).subscribe(() => {
      this.inEditMode = true;
      this.addCategoryEnabled = false;
      this.categoryForm.disable();

    }, error => {
      this.errorOccurred = true;
      this.errorMessage = error;
    });
  }

  enableEditing() {
    this.inEditMode = true;
    this.addCategoryEnabled = true;
    this.categoryForm.enable();
  }

  get categoryFormControl() {
    return this.categoryForm.controls;
  }

  whiteSpaceValidator(control: AbstractControl) {
    const isWhitespace = (control.value || '').trim().length < 3;
    const isValid = !isWhitespace;
    return isValid ? null : {whitespace: true};
  }

  getPermission(): string {
    return this.authService.getUserRole();
  }
}
