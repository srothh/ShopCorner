import {Component, OnDestroy, OnInit} from '@angular/core';
import {ShopService} from './services/shop.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ShopCorner';

  constructor(private shopService: ShopService) {
  }

  ngOnInit(): void {
    this.shopService.loadSettings().subscribe();
  }
}
