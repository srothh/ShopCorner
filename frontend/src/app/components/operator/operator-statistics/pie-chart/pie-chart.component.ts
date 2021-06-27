import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Invoice} from '../../../../dtos/invoice';
import {BaseChartDirective, Color} from 'ng2-charts';
import {ChartDataSets, ChartType} from 'chart.js';
import {InvoiceType} from '../../../../dtos/invoiceType.enum';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit {

  @Input() invoices: Invoice[];
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      position: { position: 'left' },
    },
    plugins: {
      datalabels: {
        formatter: (value, ctx) => this.percentageLabels[ctx.dataIndex],
      },
    }
  };
  public chartLabels = ['Kunden', 'Betreiber'];
  public percentageLabels = [];
  public chartType: ChartType = 'pie';
  public chartLegend = true;
  public chartColors: Color[] = [
    {
      backgroundColor: ['rgba(20,160,226,0.7)', 'rgba(237,93,16,0.7)']
    }
    ];
  public pieChartPlugins = [pluginDataLabels];
  chartData: ChartDataSets[];
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.update(this.invoices);
  }

  update(invoices: Invoice[]) {
    this.invoices = invoices;
    this.percentageLabels = [];
    let operatorAmount = 0;
    let customerAmount = 0;
    for (const invoice of this.invoices) {
      if (invoice.invoiceType === InvoiceType.operator) {
        operatorAmount += invoice.amount;
      } else if (invoice.invoiceType === InvoiceType.customer) {
        customerAmount += invoice.amount;
      }
    }
    operatorAmount = Math.round(operatorAmount * 100) / 100;
    customerAmount = Math.round(customerAmount * 100) / 100;
    const overallAmount = customerAmount + operatorAmount;
    const opPercentage = operatorAmount / overallAmount * 100;
    const custPercentage = customerAmount / overallAmount * 100;
    this.percentageLabels.push(Math.round(custPercentage * 100) / 100 + '%');
    this.percentageLabels.push(Math.round(opPercentage * 100) / 100 + '%');
    this.chartData = [{data: [customerAmount, operatorAmount], label: 'Amount'}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }
}
