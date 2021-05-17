export class Address {
  constructor(
    public id: number,
    public street: string,
    public postalCode: number,
    public houseNumber: string,
    public stairNumber: number,
    public doorNumber: string
  ) {
  }
}
