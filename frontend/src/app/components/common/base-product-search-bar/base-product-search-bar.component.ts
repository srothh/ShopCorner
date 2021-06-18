import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {faFilter} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CategoryService} from '../../../services/category.service';
import {Category} from '../../../dtos/category';

@Component({
  selector: 'app-base-product-search-bar',
  templateUrl: './base-product-search-bar.component.html',
  styleUrls: ['./base-product-search-bar.component.scss']
})
export class BaseProductSearchBarComponent implements OnInit {
  @Output() resetAndFetchProducts: EventEmitter<any> = new EventEmitter<any>();
  @Output() errorOccurred: EventEmitter<any> = new EventEmitter<any>();

  searchForm: FormGroup;
  categories: Category[];

  faFilter = faFilter;

  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService) {
    this.searchForm = this.formBuilder.group({
      searchText: [''],
      categoryId: [-1]
    });
  }

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories() {
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
    }, error => {
      this.error = true;
      if (typeof error.error === 'object') {
        this.errorMessage = error.error.error;
      } else {
        this.errorMessage = error.error;
      }
      this.errorOccurred.emit({error, errorMessage: `Categories - ${this.errorMessage}`});
    });
  }

  filterChanged() {
    this.resetAndFetchProducts.emit(this.searchForm);
  }

}
