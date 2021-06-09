import {InvoiceItem} from './invoiceItem';

export class Invoice {
  id: number;
  invoiceNumber: string;
  date: string;
  amount: number;
  items: InvoiceItem[];
  constructor(
    ) {
    this.items = [];
  }

}
