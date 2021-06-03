import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopAccountOrdersComponent } from './shop-account-orders.component';

describe('AccountOrdersComponent', () => {
  let component: ShopAccountOrdersComponent;
  let fixture: ComponentFixture<ShopAccountOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopAccountOrdersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopAccountOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
