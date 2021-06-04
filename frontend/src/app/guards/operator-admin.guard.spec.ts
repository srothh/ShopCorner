import { TestBed } from '@angular/core/testing';

import { OperatorAdminGuard } from './operator-admin.guard';

describe('OperatorAdminGuard', () => {
  let guard: OperatorAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OperatorAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
