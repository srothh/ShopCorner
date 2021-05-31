import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorInvoiceOverviewComponent } from './operator-invoice-overview.component';

describe('OperatorInvoiceOverviewComponent', () => {
  let component: OperatorInvoiceOverviewComponent;
  let fixture: ComponentFixture<OperatorInvoiceOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorInvoiceOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorInvoiceOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
