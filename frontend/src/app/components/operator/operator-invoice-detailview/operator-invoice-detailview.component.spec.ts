import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorInvoiceDetailviewComponent } from './operator-invoice-detailview.component';

describe('OperatorInvoiceDetailviewComponent', () => {
  let component: OperatorInvoiceDetailviewComponent;
  let fixture: ComponentFixture<OperatorInvoiceDetailviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorInvoiceDetailviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorInvoiceDetailviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
