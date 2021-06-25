import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Product} from '../../../../dtos/product';
import {BaseChartDirective} from 'ng2-charts';
import {ChartDataSets, ChartType} from 'chart.js';

@Component({
  selector: 'app-products-top-money-chart',
  templateUrl: './products-top-money-chart.component.html',
  styleUrls: ['./products-top-money-chart.component.scss']
})
export class ProductsTopMoneyChartComponent implements OnInit {

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

  changeView() {
    this.topTen = !this.topTen;
    this.update(this.products);
  }

  update(products: Product[]) {
    this.products = products;
    this.chartLabels = [];
    const temp = [];
    this.products.sort((b, a) => (a.saleCount * a.price) - (b.saleCount * b.price));
    for (const product of this.products) {
      if (this.topTen && temp.length === 10) {
        break;
      }
      temp.push(product.saleCount * product.price);
      this.chartLabels.push(product.name);
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
