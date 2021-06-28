import {Component, OnInit} from '@angular/core';
import {ShopService} from './services/shop/shop.service';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private shopService: ShopService, private titleService: Title) {
  }

  ngOnInit(): void {
    this.shopService.loadSettings().subscribe();
    this.titleService.setTitle(this.shopService.getSettings().title);
  }
}
