import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorAddProductComponent } from './operator-add-product.component';

describe('OperatorAddProductComponent', () => {
  let component: OperatorAddProductComponent;
  let fixture: ComponentFixture<OperatorAddProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorAddProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorAddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
