import {Invoice} from './invoice';
import {Customer} from './customer';
import {Promotion} from './promotion';

export class Order {

  constructor(
    public id: number,
    public invoice: Invoice,
    public customer: Customer,
    public promotion: Promotion
  ) {
  }

}
