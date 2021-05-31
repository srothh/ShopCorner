import {InvoiceItem} from './invoiceItem';

export class Invoice {
  id: number;
  date: string;
  amount: number;
  items: InvoiceItem[];
  constructor(
    ) {
    this.items = [];
  }

}
