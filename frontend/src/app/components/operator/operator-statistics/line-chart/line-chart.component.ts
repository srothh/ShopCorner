import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartDataSets} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {Invoice} from '../../../../dtos/invoice';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit {

  @Input() invoices: Invoice[];
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public chartLabels = [];
  public chartType = 'line';
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
    for (const invoice of this.invoices) {
      temp.push(invoice.amount);
      this.chartLabels.push(invoice.invoiceNumber);
    }
    this.chartData = [{data: temp, label: this.invoices[1].invoiceType}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }

}
