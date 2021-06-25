import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartDataSets, ChartType} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {Invoice} from '../../../../dtos/invoice';
import {InvoiceType} from '../../../../dtos/invoiceType.enum';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit {

  @Input() invoices: Invoice[];
  @Input() start: Date;
  @Input() end: Date;
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [
        {
          stacked: true,
        }
      ]
    }
  };
  public chartLabels = [];
  public chartType: ChartType = 'line';
  public chartLegend = true;
  chartData: ChartDataSets[];
  showDetail = false;
  temp = [];
  tempCu = [];
  tempOp = [];
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.update(this.start, this.end, this.invoices);
  }

  update(start: Date, end: Date, invoices: Invoice[]) {
    this.start = start;
    this.end = end;
    this.invoices = invoices;
    this.chartLabels = [];
    this.temp = [];
    this.tempCu = [];
    this.tempOp = [];
    const days = [];
    for (let d = new Date(this.start); d<= this.end; d.setDate(d.getDate() + 1)) {
      days.push(d.toISOString().split('T')[0]);
      this.temp.push(0);
      this.tempOp.push(0);
      this.tempCu.push(0);
      this.chartLabels.push(d.toISOString().split('T')[0]);
    }
    for (const invoice of this.invoices) {
      if (invoice.invoiceType !== InvoiceType.canceled) {
        const day = invoice.date.substring(0, 10);
        this.temp[days.indexOf(day)] += invoice.amount;
        if (invoice.invoiceType === InvoiceType.customer) {
          this.tempCu[days.indexOf(day)] += invoice.amount;
        } else {
          this.tempOp[days.indexOf(day)] += invoice.amount;
        }
      }
    }
    this.fillData();
  }

  fillData() {
    if (this.showDetail) {
      this.chartData = [{data: this.tempCu, label: 'Kunden'}, {data: this.tempOp, label: 'Betreiber'}];
    } else {
      this.chartData = [{data: this.temp, label: 'Umsatz'}];
    }
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }

  changeView() {
    this.showDetail = !this.showDetail;
    this.fillData();
  }
}
