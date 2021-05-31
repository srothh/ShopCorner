import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ShopAccountComponent} from './shop-account.component';

describe('ShopAccountComponent', () => {
  let component: ShopAccountComponent;
  let fixture: ComponentFixture<ShopAccountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
