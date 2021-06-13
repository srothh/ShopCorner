import {Permissions} from './permissions.enum';
import {Invoice} from './invoice';
import {Customer} from './customer';

export class Order {

  constructor(
    public id: number,
    public invoice: Invoice,
    public customer: Customer
    ) {
  }

}
