import {Category} from './category';
import {TaxRate} from './tax-rate';

export class Product {
  quantity: number;
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public price: number,
    public category: Category,
    public taxRate: TaxRate,
    public locked: boolean,
    public picture: string | ArrayBuffer,
    public deleted: boolean,
  ) {
  }
}
