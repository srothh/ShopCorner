import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/customer-auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './shop-home.component.html',
  styleUrls: ['./shop-home.component.scss']
})
export class ShopHomeComponent implements OnInit {

  constructor(public authService: CustomerAuthService) { }

  ngOnInit() {
  }

}
