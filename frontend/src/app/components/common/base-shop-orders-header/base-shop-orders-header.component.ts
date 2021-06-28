import {Component, Input, OnInit} from '@angular/core';
import {Order} from '../../../dtos/order';
import {MeService} from '../../../services/me/me.service';
import {OrderService} from '../../../services/order/order.service';
import {NgdbModalActionComponent} from '../ngbd-modal-action/ngdb-modal-action.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-base-shop-orders-header',
  templateUrl: './base-shop-orders-header.component.html',
  styleUrls: ['./base-shop-orders-header.component.scss']
})
export class BaseShopOrdersHeaderComponent implements OnInit {
  @Input()
  order: Order;
  error: boolean;
  errorMessage: boolean;
  isCanceled = false;
  isCancelable = false;
  constructor(private meService: MeService,
              private orderService: OrderService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.isCanceled = this.order.invoice.invoiceType === 'canceled';
    this.cancellationPeriod();
  }

  downloadInvoice(event: Event, invoiceId: number, invoiceDate: string) {
    event.preventDefault();
    this.meService.getInvoiceAsPdfByIdForCustomer(invoiceId).subscribe((data) => {
      const downloadURL = window.URL.createObjectURL(data);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'invoice_' + invoiceDate + '_' + invoiceId + '.pdf';
      link.click();
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  attemptToCancelInvoiceModal() {
    const modalRef = this.modalService.open(NgdbModalActionComponent);
    modalRef.componentInstance.title = 'Stornieren';
    modalRef.componentInstance.body = 'Wollen Sie die Rechnung unwiderruflich stornieren?';
    modalRef.componentInstance.actionButtonTitle = 'Stornieren';
    modalRef.componentInstance.actionButtonStyle = 'danger';
    modalRef.componentInstance.action = () => {
      this.canceledOrder();
    };
  }

  canceledOrder() {
    this.orderService.setOrderCanceled(this.order).subscribe(() => {
      this.isCanceled = true;
    }, (error) => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  cancellationPeriod() {
    this.orderService.getCancellationPeriod().subscribe((item) => {
        const date = this.order.invoice.date;
        const invoiceDate = new Date(date);
        const today = new Date();
        invoiceDate.setDate(invoiceDate.getDate() + item.days);
        this.isCancelable = invoiceDate > today;
      }, (error) => {
        this.error = true;
        this.errorMessage = error;
      });
  }
}
