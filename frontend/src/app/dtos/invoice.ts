import {Product} from './product';

export class Invoice {
  id: number;
  date: string;
  amount: number;
  invoiceItems: Product[] = [];
  constructor(
    ) {
  }

}
