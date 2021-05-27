import { Component, OnInit } from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../dtos/product';
import {Router, UrlSerializer} from '@angular/router';
import {forkJoin} from 'rxjs';
import {Category} from '../../../dtos/category';
import {TaxRate} from '../../../dtos/tax-rate';
import {CategoryService} from '../../../services/category.service';
import {TaxRateService} from '../../../services/tax-rate.service';

@Component({
  selector: 'app-operator-products',
  templateUrl: './operator-product.component.html',
  styleUrls: ['./operator-product.component.scss']
})
export class OperatorProductComponent implements OnInit {
  products: Product[];
  categories: Category[];
  taxRates: TaxRate[];
  page = 0;
  pageSize = 15;
  collectionSize = 0;
  clickedCheckmark = false;
  selectedProducts: Product[] = [];
  constructor(private productService: ProductService, private router: Router, private urlSerializer: UrlSerializer,
              private categoryService: CategoryService, private taxRateService: TaxRateService) { }
  ngOnInit(): void {
    this.fetchData();
  }
  fetchData(): void {
    forkJoin([this.productService.getProducts(this.page, this.pageSize),
      this.categoryService.getCategories(), this.taxRateService.getTaxRates(),
      this.productService.getNumberOfProducts()])
      .subscribe(([productsData, categoriesData,taxRatesData, numberOfProducts]) => {
        this.products = productsData;
        this.collectionSize = numberOfProducts;
        this.categories = categoriesData;
        this.taxRates = taxRatesData;
      });

  }
  fetchProducts(): void{
    this.productService.getProducts(this.page, this.pageSize).subscribe((productData) => {
      this.products = productData;
    });
  }
  addNewProduct(): void {
    const currentURL = this.urlSerializer.serialize(this.router.createUrlTree([]));
    const addProductURL = currentURL.replace('products','products/add');
    this.router.navigate([addProductURL],{state: [this.categories,this.taxRates]}).then();
  }
  goToProductDetails(selectedProduct: Product, event){
    // if checkbox was clicked then stop cell-click-event routing to details page
    // checkbox is an input type with an corresponding label type
    if (event.target.toString().includes('HTMLLabelElement') || event.target.toString().includes('HTMLInputLabel')) {
      event.stopPropagation();
    } else {
      const currentUri = this.urlSerializer.serialize(this.router.createUrlTree([]));
      this.router.navigate([currentUri + '/' + selectedProduct.id], {state: [this.categories, this.taxRates, selectedProduct]}).then();
    }
  }
  clickedCheckMark(event, index: number){
    // no propagation to details site allowed when clicking the checkbox
    // NOTE: I'm not sure why we need to stop propagation on this method and in goToProductDetails()
    event.stopPropagation();
    if (event.target.checked) {
      console.log(this.products[index]);
      this.selectedProducts.push(this.products[index]);
    } else {
      const product = this.products[index];
      const deleteIndex = this.selectedProducts.indexOf(product);
      this.selectedProducts.splice(deleteIndex, 1);
    }
  }
  deleteProducts(){
    if (this.selectedProducts.length > 0){
      for (const selectedProduct of this.selectedProducts){
        this.productService.deleteProduct(selectedProduct.id).subscribe(()=>{
           this.fetchProducts();
        });
      }
      this.collectionSize -= this.selectedProducts.length;
    }
  }
  previousPage(): void {
    if (this.page > 0) {
      this.page -= 1;
      this.fetchProducts();
    }
  }
  nextPage(): void {
    if ((this.page + 1) * this.pageSize < this.collectionSize) {
      this.page += 1;
      this.fetchProducts();
    }
  }
}
