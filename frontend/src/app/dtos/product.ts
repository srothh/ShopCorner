import {Category} from './category';
import {TaxRate} from './tax-rate';

export class Product {
  cartItemId: number;
  cartItemQuantity: number;
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public price: number,
    public category: Category,
    public taxRate: TaxRate,
    public locked: boolean,
    public picture: string | ArrayBuffer,
    public expiresAt: Date,
    public deleted: boolean,
  ) {
  }

  get availabilityText() {
    if (this.locked) {
      return 'Nicht verfügbar';
    }
    if (this.hasExpiration) {
      return `Bis ${this.formattedDateString} verfügbar`;
    }
    return 'Auf Lager';
  }

  get hasExpiration() {
    return this.expiresAt !== null;
  }

  get formattedDateString() {
    const date = new Date(this.expiresAt);
    const year = new Intl.DateTimeFormat('de', { year: 'numeric' }).format(date);
    const month = new Intl.DateTimeFormat('de', { month: 'short' }).format(date);
    const day = new Intl.DateTimeFormat('de', { day: '2-digit' }).format(date);
    const time = date.toLocaleTimeString('de');
    return `${day}. ${month} ${year}, ${time}`;
  }

}
