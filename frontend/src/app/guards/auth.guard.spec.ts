import {inject, TestBed} from '@angular/core/testing';

import {CustomerAuthGuard} from './customer-auth-guard.service';

describe('AuthGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CustomerAuthGuard]
    });
  });

  it('should ...', inject([CustomerAuthGuard], (guard: CustomerAuthGuard) => {
    expect(guard).toBeTruthy();
  }));
});
