import {Component, OnInit} from '@angular/core';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {faShoppingCart} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  faShoppingCart = faShoppingCart;

  constructor(public authService: CustomerAuthService) { }

  ngOnInit() {
  }

}
