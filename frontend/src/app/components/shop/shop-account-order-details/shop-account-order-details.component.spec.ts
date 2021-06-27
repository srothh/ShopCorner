import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopAccountOrderDetailsComponent } from './shop-account-order-details.component';

describe('ShopAccountOrderDetailsComponent', () => {
  let component: ShopAccountOrderDetailsComponent;
  let fixture: ComponentFixture<ShopAccountOrderDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopAccountOrderDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopAccountOrderDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
