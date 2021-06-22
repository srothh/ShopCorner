import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Invoice} from '../../../../dtos/invoice';
import {BaseChartDirective} from 'ng2-charts';
import {ChartDataSets} from 'chart.js';
import {InvoiceType} from '../../../../dtos/invoiceType.enum';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent implements OnInit {

  @Input() invoices: Invoice[];
  @Input() start: Date;
  @Input() end: Date;
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public chartLabels = [];
  public chartType = 'bar';
  public chartLegend = true;
  chartData: ChartDataSets[];
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.update();
  }

  update() {
    const tempOp = [];
    const tempCu = [];
    const months = [];
    for (let d = new Date(this.start); d<= this.end; d.setMonth(d.getMonth() + 1)) {
      console.log('Hello');
      const month = d.toISOString().split('T')[0];
      months.push(month.substring(0,7));
      tempOp.push(0);
      tempCu.push(0);
      this.chartLabels.push(month.substring(0,7));
    }
    for (const invoice of this.invoices) {
      const month = invoice.date.substring(0,7);
      if (invoice.invoiceType === InvoiceType.operator) {
        tempOp[months.indexOf(month)] += invoice.amount;
      }
      if (invoice.invoiceType === InvoiceType.customer) {
        tempCu[months.indexOf(month)] += invoice.amount;
      }
    }
    this.chartData = [{data: tempOp, label: 'Betreiber'}, {data: tempCu, label: 'Kunden'}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }
}
