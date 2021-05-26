import { TestBed } from '@angular/core/testing';

import { OperatorAuthGuard } from './operator-auth.guard';

describe('OperatorAuthGuard', () => {
  let guard: OperatorAuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OperatorAuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
