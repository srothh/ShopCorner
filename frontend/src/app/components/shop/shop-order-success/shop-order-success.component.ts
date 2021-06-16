import { Component, OnInit } from '@angular/core';
import {faCheckCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-shop-order-success',
  templateUrl: './shop-order-success.component.html',
  styleUrls: ['./shop-order-success.component.scss']
})
export class ShopOrderSuccessComponent implements OnInit {
  faCheckCircle = faCheckCircle;

  constructor() { }

  ngOnInit(): void {
  }

}
