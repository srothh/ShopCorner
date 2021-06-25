import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseShopOrdersHeaderComponent } from './base-shop-orders-header.component';

describe('BaseShopOrdersHeaderComponent', () => {
  let component: BaseShopOrdersHeaderComponent;
  let fixture: ComponentFixture<BaseShopOrdersHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseShopOrdersHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseShopOrdersHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
