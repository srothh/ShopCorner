import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ShopHomeComponent} from './shop-home.component';

describe('HomeComponent', () => {
  let component: ShopHomeComponent;
  let fixture: ComponentFixture<ShopHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
