import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseShopOrderItemComponent } from './base-shop-order-item.component';

describe('BaseShopOrderItemComponent', () => {
  let component: BaseShopOrderItemComponent;
  let fixture: ComponentFixture<BaseShopOrderItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseShopOrderItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseShopOrderItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
