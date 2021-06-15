import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorCategoryDetailsComponent } from './operator-category-details.component';

describe('OperatorCategoryDetailsComponent', () => {
  let component: OperatorCategoryDetailsComponent;
  let fixture: ComponentFixture<OperatorCategoryDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorCategoryDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorCategoryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
