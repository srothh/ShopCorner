import {InvoiceItemKey} from './invoiceItemKey';
import {Invoice} from './invoice';
import {Product} from './product';

export class InvoiceItem {
  id: InvoiceItemKey;
  invoice: Invoice;
  product: Product;
  numberOfItems: number;
  constructor(id: InvoiceItemKey, product: Product, numberOfItems: number) {
    this.id = id;
    this.product = product;
    this.numberOfItems = numberOfItems;
  }
}
