import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorAddCategoryComponent } from './operator-add-category.component';

describe('OperatorAddCategoryComponent', () => {
  let component: OperatorAddCategoryComponent;
  let fixture: ComponentFixture<OperatorAddCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorAddCategoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorAddCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
