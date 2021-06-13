import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../dtos/customer';
import {MeService} from '../../../services/me.service';

@Component({
  selector: 'app-shop-checkout',
  templateUrl: './shop-checkout.component.html',
  styleUrls: ['./shop-checkout.component.scss']
})
export class ShopCheckoutComponent implements OnInit {

  customer: Customer;

  constructor(private meService: MeService) {
  }

  ngOnInit(): void {
    this.fetchCustomer();
  }

  getStreetAddress(): string {
    return this.customer.address.street + ' ' + this.customer.address.houseNumber + (this.customer.address.doorNumber
      ? '/' + this.customer.address.doorNumber : '');
  }

  private fetchCustomer() {
    this.meService.getMyProfileData().subscribe(
      (customer: Customer) => {
        this.customer = customer;
      }
    );
  }


}
