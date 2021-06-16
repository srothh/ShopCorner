import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorCategoryFormComponent } from './operator-category-form.component';

describe('OperatorCategoryFormComponent', () => {
  let component: OperatorCategoryFormComponent;
  let fixture: ComponentFixture<OperatorCategoryFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorCategoryFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorCategoryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
