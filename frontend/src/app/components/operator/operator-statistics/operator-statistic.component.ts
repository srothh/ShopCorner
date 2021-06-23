import {Component, OnInit, ViewChild} from '@angular/core';
import {Invoice} from '../../../dtos/invoice';
import {InvoiceService} from '../../../services/invoice.service';
import {InvoiceType} from '../../../dtos/invoiceType.enum';
import {LineChartComponent} from './line-chart/line-chart.component';
import {PieChartComponent} from './pie-chart/pie-chart.component';
import {BarChartComponent} from './bar-chart/bar-chart.component';

@Component({
  selector: 'app-operator-statistics',
  templateUrl: './operator-statistic.component.html',
  styleUrls: ['./operator-statistic.component.scss']
})
export class OperatorStatisticComponent implements OnInit {

  @ViewChild(LineChartComponent) lineChild: LineChartComponent;
  @ViewChild(PieChartComponent) pieChild: PieChartComponent;
  @ViewChild(BarChartComponent) barChild: BarChartComponent;

  error = false;
  errorMessage = '';
  invoices: Invoice[];
  invoiceType = InvoiceType.operator;
  end = new Date();
  start = new Date();
  startPicker: string;
  endPicker: string;
  loaded = false;

  constructor(private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.start.setDate(this.start.getDate()-90);
    this.startPicker = this.start.toISOString().split('T')[0];
    this.endPicker = this.end.toISOString().split('T')[0];
    this.loadInvoicesForTime();
  }

  update() {
    this.start = new Date(this.startPicker);
    this.end = new Date(this.endPicker);
    this.loadInvoicesForTime();
  }

  getMonth(month: string) {
    this.start =  new Date(month);
    this.end = new Date(this.start);
    this.end.setMonth(this.end.getMonth()+1);
    this.end.setDate(this.end.getDate()-1);
    this.startPicker = this.start.toISOString().split('T')[0];
    this.endPicker = this.end.toISOString().split('T')[0];
    this.loadInvoicesForTime();
  }

  updateChildren() {
    this.lineChild.update(this.start, this.end, this.invoices);
    this.barChild.update(this.start, this.end, this.invoices);
    this.pieChild.update(this.invoices);
  }

  /**
   * calls on Service class to fetch all customer accounts from backend
   */
  private loadInvoicesForTime() {
    this.invoiceService.getAllInvoicesByDate(this.start, this.end).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
        if (this.loaded === true) {
          this.updateChildren();
        } else {
          this.loaded = true;
        }
      },
      error => {
        this.error = true;
        this.errorMessage = error.error;
      }
    );
  }
}
