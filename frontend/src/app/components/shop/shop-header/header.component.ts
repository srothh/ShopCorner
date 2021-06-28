import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {faShoppingCart} from '@fortawesome/free-solid-svg-icons';
import {Globals} from '../../../global/globals';
import {CartGlobals} from '../../../global/cartGlobals';
import {ShopService} from '../../../services/shop/shop.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  faShoppingCart = faShoppingCart;
  title = this.globals.defaultSettings.title;

  constructor(public authService: CustomerAuthService,
              private globals: Globals,
              private cartGlobals: CartGlobals,
              private shopService: ShopService) { }

  ngOnInit() {
    this.title = this.shopService.getSettings().title;
  }

  getCartSize() {
    return this.cartGlobals.getCartSize();
  }
}
