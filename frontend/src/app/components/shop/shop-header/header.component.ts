import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {faShoppingCart} from '@fortawesome/free-solid-svg-icons';
import {Globals} from '../../../global/globals';
import {CartGlobals} from '../../../global/cartGlobals';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  faShoppingCart = faShoppingCart;

  constructor(public authService: CustomerAuthService, private globals: Globals, private cartGlobals: CartGlobals) { }

  ngOnInit() {
  }
  getCartSize() {
    return this.cartGlobals.getCartSize();
  }
}
