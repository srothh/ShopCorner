import {Component, OnInit} from '@angular/core';
import {ShopService} from './services/shop/shop.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private shopService: ShopService) {
  }

  ngOnInit(): void {
    this.shopService.loadSettings().subscribe();
  }
}
