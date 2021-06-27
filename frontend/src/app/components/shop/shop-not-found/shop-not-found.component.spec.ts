import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopNotFoundComponent } from './shop-not-found.component';

describe('ShopNotFoundComponent', () => {
  let component: ShopNotFoundComponent;
  let fixture: ComponentFixture<ShopNotFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopNotFoundComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopNotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
