import {Component, OnInit} from '@angular/core';
import {faCheckCircle} from '@fortawesome/free-solid-svg-icons';
import {PaypalService} from '../../../services/paypal.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-shop-order-success',
  templateUrl: './shop-order-success.component.html',
  styleUrls: ['./shop-order-success.component.scss']
})
export class ShopOrderSuccessComponent implements OnInit {
  faCheckCircle = faCheckCircle;
  paymentId: string;
  payerId: string;
  paymentSucceeded: boolean;

  constructor(private paypalService: PaypalService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.paymentId = params['paymentId'];
      this.payerId = params['PayerID'];
      this.paypalService.confirmPayment(this.payerId, this.paymentId).subscribe((finalisedPaymentData) => {
        if (finalisedPaymentData.includes('Payment successful')) {
          this.paymentSucceeded = true;
        }
      }, error => {
        this.paymentSucceeded = false;
      } );
    });
  }
}
