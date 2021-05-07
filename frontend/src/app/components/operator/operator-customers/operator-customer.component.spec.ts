import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorCustomerComponent } from './operator-customer.component';

describe('OperatorCustomersComponent', () => {
  let component: OperatorCustomerComponent;
  let fixture: ComponentFixture<OperatorCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorCustomerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
