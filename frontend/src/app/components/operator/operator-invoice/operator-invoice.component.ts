import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-operator-invoice',
  templateUrl: './operator-invoice.component.html',
  styleUrls: ['./operator-invoice.component.scss']
})
export class OperatorInvoiceComponent implements OnInit {
  toggle = false;
  constructor() { }

  ngOnInit(): void {
    this.toggle = false;
  }

  toggleSide() {
    this.toggle = !this.toggle;
    console.log(this.toggle);
  }

}
