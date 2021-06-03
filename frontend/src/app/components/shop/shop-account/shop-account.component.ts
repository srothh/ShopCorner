import {Component, OnInit} from '@angular/core';
import {faShippingFast, faShieldAlt} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-shop-account',
  templateUrl: './shop-account.component.html',
  styleUrls: ['./shop-account.component.scss']
})
export class ShopAccountComponent implements OnInit {

  faShippingFast = faShippingFast;
  faShieldAlt = faShieldAlt;

  ngOnInit(): void {
  }
}
