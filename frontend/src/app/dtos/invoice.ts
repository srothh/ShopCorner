import {InvoiceItem} from './invoiceItem';
import {InvoiceType} from './invoiceType.enum';

export class Invoice {
  id: number;
  orderNumber: string;
  invoiceNumber: string;
  customer: number;
  date: string;
  amount: number;
  items: InvoiceItem[];
  type: InvoiceType;
  constructor(
    ) {
    this.items = [];
  }

}
