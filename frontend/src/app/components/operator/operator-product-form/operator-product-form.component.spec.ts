import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorProductFormComponent } from './operator-product-form.component';

describe('OperatorProductFormComponent', () => {
  let component: OperatorProductFormComponent;
  let fixture: ComponentFixture<OperatorProductFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorProductFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorProductFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
