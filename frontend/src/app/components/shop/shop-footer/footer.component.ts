import {Component, OnInit} from '@angular/core';
import {ShopService} from '../../../services/shop.service';
import {Globals} from '../../../global/globals';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  logo = this.globals.defaultSettings.logo;
  title = this.globals.defaultSettings.title;

  constructor(private shopService: ShopService, private globals: Globals) { }

  ngOnInit() {
    this.configureFooter();
  }

  private configureFooter() {
    const settings = this.shopService.getSettings();
    this.logo = settings.logo;
    this.title = settings.title;
  }
}
