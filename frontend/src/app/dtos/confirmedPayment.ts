export class ConfirmedPayment {
  constructor(
    public id: number,
    public paymentId: string,
    public payerId: string,
  ) {}
}
