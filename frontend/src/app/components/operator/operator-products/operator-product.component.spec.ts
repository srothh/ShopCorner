import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorProductComponent } from './operator-product.component';

describe('OperatorProductsComponent', () => {
  let component: OperatorProductComponent;
  let fixture: ComponentFixture<OperatorProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
