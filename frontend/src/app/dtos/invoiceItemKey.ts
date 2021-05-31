export class InvoiceItemKey {
  invoiceId: number;
  productId: number;
  constructor(productId: number) {
    this.invoiceId = null;
    this.productId = productId;
  }
}
