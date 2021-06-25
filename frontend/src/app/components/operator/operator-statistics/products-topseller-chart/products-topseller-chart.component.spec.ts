import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsTopsellerChartComponent } from './products-topseller-chart.component';

describe('ProductsTopsellerChartComponent', () => {
  let component: ProductsTopsellerChartComponent;
  let fixture: ComponentFixture<ProductsTopsellerChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductsTopsellerChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsTopsellerChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
