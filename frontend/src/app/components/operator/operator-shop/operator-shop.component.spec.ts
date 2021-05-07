import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorShopComponent } from './operator-shop.component';

describe('OperatorShopComponent', () => {
  let component: OperatorShopComponent;
  let fixture: ComponentFixture<OperatorShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorShopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
