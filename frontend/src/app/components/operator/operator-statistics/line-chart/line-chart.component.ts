import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartDataSets, ChartType} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {Invoice} from '../../../../dtos/invoice';

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
    responsive: true
  };
  public chartLabels = [];
  public chartType: ChartType = 'line';
  public chartLegend = true;
  chartData: ChartDataSets[];
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.update();
  }

  update() {
    const temp = [];
    const days = [];
    for (let d = new Date(this.start); d<= this.end; d.setDate(d.getDate() + 1)) {
      days.push(d.toISOString().split('T')[0]);
      temp.push(0);
      this.chartLabels.push(d.toISOString().split('T')[0]);
    }
    for (const invoice of this.invoices) {
      const day = invoice.date.substring(0,10);
      temp[days.indexOf(day)] += invoice.amount;
    }
    this.chartData = [{data: temp, label: 'Umsatz'}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }

}
