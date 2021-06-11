import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product.service';
import {Globals} from '../../../global/globals';
import {CartGlobals} from '../../../global/cartGlobals';
import {CartItem} from '../../../dtos/cartItem';
import {CartService} from '../../../services/cart.service';

@Component({
  selector: 'app-shop-product-details',
  templateUrl: './shop-product-details.component.html',
  styleUrls: ['./shop-product-details.component.scss']
})
export class ShopProductDetailsComponent implements OnInit {

  errror = false;
  errorrMessage = '';

  product: Product;

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private productService: ProductService, private globals: Globals,
              private cartGlobals: CartGlobals, private cartService: CartService) {
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
    const index = this.cartGlobals.containsProductAtIndex(product);
    if (index === -1) {
      this.cartGlobals.addToCart(product);
      this.cartService.addProductsToCart(new CartItem(product.id, 1)).subscribe( (item) => {
       if (item.cartItems.length !== this.cartGlobals.getCartSize()) {
         console.log('update cart');
       }
      }, (error) => {
        this.errror = true;
        this.errorrMessage = error;
      });

    } else {
      const cart  = this.cartGlobals.getCart();
      const quantity = cart[this.cartGlobals.containsProductAtIndex(product)]['quantity'] + 1;
      cart[this.cartGlobals.containsProductAtIndex(product)]['quantity'] = quantity;
      this.cartGlobals.updateCart(product, quantity);

      this.cartService.updateToCart(new CartItem(product.id, quantity)).subscribe( (item) => {
          console.log(item, '<<---');
      }, (error) => {
        this.errror = true;
        this.errorrMessage = error;
      });
    }
    this.router.navigate(['cart']).then();
  }

  private fetchProduct(id: number) {
    this.productService.getProductById(id).subscribe((product) => {
      this.product = product;
    });
  }
}
