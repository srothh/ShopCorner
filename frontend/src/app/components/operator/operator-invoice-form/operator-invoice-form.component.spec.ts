import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorInvoiceFormComponent } from './operator-invoice-form.component';

describe('OperatorInvoiceFormComponent', () => {
  let component: OperatorInvoiceFormComponent;
  let fixture: ComponentFixture<OperatorInvoiceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorInvoiceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorInvoiceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
