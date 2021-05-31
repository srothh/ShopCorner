import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorCategoriesComponent } from './operator-categories.component';

describe('OperatorCategoriesComponent', () => {
  let component: OperatorCategoriesComponent;
  let fixture: ComponentFixture<OperatorCategoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorCategoriesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
