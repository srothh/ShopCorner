import {Component, OnInit} from '@angular/core';
import {ShopService} from '../../../services/shop/shop.service';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  logo = this.globals.defaultSettings.logo;
  title = this.globals.defaultSettings.title;
  street = this.globals.defaultSettings.street;
  houseNumber = this.globals.defaultSettings.houseNumber;
  stairNumber = this.globals.defaultSettings.stairNumber;
  doorNumber = this.globals.defaultSettings.doorNumber;
  postalCode = this.globals.defaultSettings.postalCode;
  city = this.globals.defaultSettings.city;
  phoneNumber = this.globals.defaultSettings.phoneNumber;
  email = this.globals.defaultSettings.email;

  constructor(private shopService: ShopService, private globals: Globals) { }

  ngOnInit() {
    this.configureFooter();
  }

  get streetFormatted() {
    return `${this.street} ${this.houseNumber}`;
  }

  get locationFormatted() {
    return `${this.postalCode} ${this.city}`;
  }

  get contactFormatted() {
    return `${this.phoneNumber} ${this.email}`;
  }

  private configureFooter() {
    const settings = this.shopService.getSettings();
    this.logo = settings.logo;
    this.title = settings.title;
    this.street = settings.street;
    this.houseNumber = settings.houseNumber;
  }
}
