import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {faShoppingCart} from '@fortawesome/free-solid-svg-icons';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  faShoppingCart = faShoppingCart;

  constructor(public authService: CustomerAuthService, private globals: Globals) { }

  ngOnInit() {
  }
  getCartSize() {
    return this.globals.getCartSize();
  }
}
