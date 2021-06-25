import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsTopMoneyChartComponent } from './products-top-money-chart.component';

describe('ProductsTopMoneyChartComponent', () => {
  let component: ProductsTopMoneyChartComponent;
  let fixture: ComponentFixture<ProductsTopMoneyChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductsTopMoneyChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsTopMoneyChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
