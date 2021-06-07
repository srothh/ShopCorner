import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorInvoiceComponent } from './operator-invoice-form.component';

describe('OperatorInvoiceComponent', () => {
  let component: OperatorInvoiceComponent;
  let fixture: ComponentFixture<OperatorInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorInvoiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
