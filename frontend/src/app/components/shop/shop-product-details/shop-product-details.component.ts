import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product.service';
import {Globals} from '../../../global/globals';
import {CartGlobals} from '../../../global/CartGlobals';

@Component({
  selector: 'app-shop-product-details',
  templateUrl: './shop-product-details.component.html',
  styleUrls: ['./shop-product-details.component.scss']
})
export class ShopProductDetailsComponent implements OnInit {

  product: Product;

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private productService: ProductService, private globals: Globals, private cartGlobals: CartGlobals,) {
    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      this.product = state.product;
    }
  }

  ngOnInit(): void {
    if (!this.product) {
      const id = this.activatedRoute.snapshot.paramMap.get('id');
      this.fetchProduct(Number(id));
    }
  }

  getImageSource(product: Product): string {
    if (product.picture != null) {
      return 'data:image/png;base64,' + product.picture;
    }
    return 'Error: no picture available';
  }

  addToCart(product: Product) {
    // TODO: add to cart logic ...
    this.cartGlobals.addToCart(product);
    this.router.navigate(['cart']).then();
  }

  private fetchProduct(id: number) {
    this.productService.getProductById(id).subscribe((product) => {
      this.product = product;
    });
  }
}
