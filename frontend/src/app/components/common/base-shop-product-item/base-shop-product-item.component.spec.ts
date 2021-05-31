import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseShopProductItemComponent } from './base-shop-product-item.component';

describe('BaseShopProductItemComponent', () => {
  let component: BaseShopProductItemComponent;
  let fixture: ComponentFixture<BaseShopProductItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseShopProductItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseShopProductItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
