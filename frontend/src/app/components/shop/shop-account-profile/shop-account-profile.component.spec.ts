import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopAccountProfileComponent } from './shop-account-profile.component';

describe('ShopAccountProfileComponent', () => {
  let component: ShopAccountProfileComponent;
  let fixture: ComponentFixture<ShopAccountProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopAccountProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopAccountProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
