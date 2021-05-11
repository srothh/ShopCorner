import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ShopLoginComponent} from './shop-login.component';

describe('LoginComponent', () => {
  let component: ShopLoginComponent;
  let fixture: ComponentFixture<ShopLoginComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopLoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
