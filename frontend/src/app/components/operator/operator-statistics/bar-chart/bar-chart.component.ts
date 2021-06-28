import {Component, Input, OnInit, Output, EventEmitter, ViewChild} from '@angular/core';
import {Invoice} from '../../../../dtos/invoice';
import {BaseChartDirective, Color} from 'ng2-charts';
import {ChartDataSets, ChartType} from 'chart.js';
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
  @Output() clickEvent = new EventEmitter<string>();
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };
  public chartLabels = [];
  public chartType: ChartType = 'bar';
  public chartLegend = true;
  public chartColors: Color[] = [
    {
      backgroundColor: 'rgba(20,160,226,0.7)'
    },
    {
      backgroundColor: 'rgba(237,93,16,0.7)'
    },
    {
      backgroundColor: 'rgba(10,130,16,0.7)'
    }
  ];
  chartData: ChartDataSets[];
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
    const tempOp = [];
    const tempCu = [];
    const tempCa = [];
    const months = [];
    for (const d = new Date(this.start); (d.getMonth() <= this.end.getMonth() && d.getMonth() < 11)
    || d.getFullYear() < this.end.getFullYear(); d.setMonth(d.getMonth() + 1)) {
      const month = d.toISOString().split('T')[0];
      months.push(month.substring(0, 7));
      tempOp.push(0);
      tempCu.push(0);
      tempCa.push(0);
      this.chartLabels.push(month.substring(0, 7));
    }
    if (this.end.getMonth() === 11) {
      const month = this.end.toISOString().split('T')[0];
      months.push(month.substring(0, 7));
      tempOp.push(0);
      tempCu.push(0);
      tempCa.push(0);
      this.chartLabels.push(month.substring(0, 7));
    }
    for (const invoice of this.invoices) {
      const month = invoice.date.substring(0, 7);
      if (invoice.invoiceType === InvoiceType.operator) {
        tempOp[months.indexOf(month)] += invoice.amount;
      } else if (invoice.invoiceType === InvoiceType.customer) {
        tempCu[months.indexOf(month)] += invoice.amount;
      } else {
        tempCa[months.indexOf(month)] += invoice.amount;
      }
    }
    for (let i = 0; i < tempOp.length; i++) {
      tempOp[i] = Math.round(tempOp[i] * 100) / 100;
      tempCu[i] = Math.round(tempCu[i] * 100) / 100;
      tempOp[i] = Math.round(tempOp[i] * 100) / 100;
    }
    this.chartData = [{data: tempCu, label: 'Kunden'}, {data: tempOp, label: 'Betreiber'}, {data: tempCa, label: 'Storniert'}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }

  public chartClicked(e: any): void {
    if (e.active.length > 0) {
      const chart = e.active[0]._chart;
      const activePoints = chart.getElementAtEvent(e.event);
      if ( activePoints.length > 0) {
        const clickedElementIndex = activePoints[0]._index;
        const label = chart.data.labels[clickedElementIndex];
        this.clickEvent.emit(label);
      }
    }
  }
}
