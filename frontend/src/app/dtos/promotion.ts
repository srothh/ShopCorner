export class Promotion {
  constructor(
    public id: number,
    public name: string,
    public discount: number,
    public creationDate: string,
    public expirationDate: string,
    public code: string,
    public minimumOrderValue: number
  ) {

  }
}
