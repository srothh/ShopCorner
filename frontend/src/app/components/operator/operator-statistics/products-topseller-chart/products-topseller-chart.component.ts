import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartDataSets, ChartType} from 'chart.js';
import {Product} from '../../../../dtos/product';
import {BaseChartDirective} from 'ng2-charts';

@Component({
  selector: 'app-products-topseller-chart',
  templateUrl: './products-topseller-chart.component.html',
  styleUrls: ['./products-topseller-chart.component.scss']
})
export class ProductsTopsellerChartComponent implements OnInit {

  @Input() products: Product[];
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
  chartData: ChartDataSets[];
  topTen = true;
  error = false;
  errorMessage = '';

  constructor() { }

  ngOnInit(): void {
    this.update(this.products);
  }

  viewTop() {
    if (!this.topTen) {
      this.topTen = true;
      this.update(this.products);
    }
  }

  viewAll() {
    if (this.topTen) {
      this.topTen = false;
      this.update(this.products);
    }
  }

  update(products: Product[]) {
    this.products = products;
    this.chartLabels = [];
    const temp = [];
    this.products.sort((b, a) => parseFloat(a.saleCount) - parseFloat(b.saleCount));
    for (const product of this.products) {
      if (this.topTen && temp.length === 10) {
        break;
      }
      temp.push(product.saleCount);
      this.chartLabels.push(product.name);
    }
    this.chartData = [{data: temp, label: 'Verkäufe'}];
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.config.data.labels = this.chartLabels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }
}
