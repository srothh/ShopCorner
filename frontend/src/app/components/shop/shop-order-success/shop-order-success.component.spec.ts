import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopOrderSuccessComponent } from './shop-order-success.component';

describe('ShopOrderSuccessComponent', () => {
  let component: ShopOrderSuccessComponent;
  let fixture: ComponentFixture<ShopOrderSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopOrderSuccessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopOrderSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
