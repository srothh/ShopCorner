import { Component, OnInit } from '@angular/core';
import {faEdit} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-shop-account-profile',
  templateUrl: './shop-account-profile.component.html',
  styleUrls: ['./shop-account-profile.component.scss']
})
export class ShopAccountProfileComponent implements OnInit {

  faEdit = faEdit;

  constructor() { }

  ngOnInit(): void {
  }

}
