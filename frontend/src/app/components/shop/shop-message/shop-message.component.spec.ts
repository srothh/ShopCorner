import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {ShopMessageComponent} from './shop-message.component';

describe('MessageComponent', () => {
  let component: ShopMessageComponent;
  let fixture: ComponentFixture<ShopMessageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ShopMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
