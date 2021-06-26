import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartDataSets, ChartType} from 'chart.js';
import {BaseChartDirective, Color} from 'ng2-charts';
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
  public detailChartColors: Color[] = [
    {
      backgroundColor: 'rgba(20,180,246,0.3)',
      borderColor: 'rgba(20,160,226,1)',
      pointBackgroundColor: 'rgba(20,50,100,1)',
      pointBorderColor: '#000',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,206,1)'
    },
    {
      backgroundColor: 'rgba(197,93,16,0.4)',
      borderColor: 'rgba(217,93,16,1)',
      pointBackgroundColor: 'rgba(187,53,6,1)',
      pointBorderColor: '#000',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public overallChartColors: Color[] = [
    {
      backgroundColor: 'rgba(200,20,30,0.5)',
      borderColor: 'rgba(200,20,30,1)',
      pointBackgroundColor: 'rgba(90,30,30,1)',
      pointBorderColor: '#000',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(90,30,30,0.8)'
    }
  ];
  public chartColors: Color[];
  chartData: ChartDataSets[];
  showDetail = false;
  temp = [];
  tempCu = [];
  tempOp = [];
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.chartColors = this.overallChartColors;
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
    for (let i = 0; i < this.temp.length; i++) {
      this.temp[i] = Math.round(this.temp[i] * 100) / 100;
      this.tempCu[i] = Math.round(this.tempCu[i] * 100) / 100;
      this.tempOp[i] = Math.round(this.tempOp[i] * 100) / 100;
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

  viewDetail() {
    if (!this.showDetail) {
      this.chartColors = this.detailChartColors;
      this.showDetail = true;
      this.update(this.start, this.end, this.invoices);
    }
  }

  viewOverall() {
    if (this.showDetail) {
      this.chartColors = this.overallChartColors;
      this.showDetail = false;
      this.update(this.start, this.end, this.invoices);
    }
  }
}
