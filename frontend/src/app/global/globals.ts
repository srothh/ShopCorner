import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class Globals {
  readonly backendUri: string = this.findBackendUrl();

  readonly roles = {
    admin: 'ADMIN',
    employee: 'EMPLOYEE',
    customer: 'CUSTOMER',
  };

  readonly requestParamKeys = {
    pagination: {
      page: 'page',
      pageCount: 'pageCount',
    },
    products: {
      name: 'name',
      categoryId: 'categoryId',
      sortBy: 'sortBy',
    },
    operators: {
      permissions: 'permissions',
    },
    invoice: {
      invoiceType: 'invoiceType',
    },
    paypal: {
      payerId: 'payerId',
      paymentId: 'paymentId'
    },
    date: {
      start: 'start',
      end: 'end'
    }
  };

  private findBackendUrl(): string {
    if (window.location.port === '4200') { // local `ng serve`, backend at localhost:8080
      return 'http://localhost:8080/api/v1';
    } else {
      // assume deployed somewhere and backend is available at same host/port as frontend
      return window.location.protocol + '//' + window.location.host + window.location.pathname + 'api/v1';
    }
  }



}


