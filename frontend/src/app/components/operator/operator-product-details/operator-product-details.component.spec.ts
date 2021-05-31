import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorProductDetailsComponent } from './operator-product-details.component';

describe('OperatorProductDetailsComponent', () => {
  let component: OperatorProductDetailsComponent;
  let fixture: ComponentFixture<OperatorProductDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorProductDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorProductDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
