import {InvoiceItem} from './invoiceItem';
import {InvoiceType} from './invoiceType.enum';

export class Invoice {
  id: number;
  orderNumber: string;
  invoiceNumber: string;
  customerId: number;
  date: string;
  amount: number;
  items: InvoiceItem[];
  invoiceType: InvoiceType;
  constructor(
    ) {
    this.items = [];
  }

}
