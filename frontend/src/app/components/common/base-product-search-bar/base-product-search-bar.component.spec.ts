import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseProductSearchBarComponent } from './base-product-search-bar.component';

describe('BaseProductSearchBarComponent', () => {
  let component: BaseProductSearchBarComponent;
  let fixture: ComponentFixture<BaseProductSearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseProductSearchBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseProductSearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
