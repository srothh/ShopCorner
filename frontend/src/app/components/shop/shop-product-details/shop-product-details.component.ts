import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {Product} from '../../../dtos/product';
import {ProductService} from '../../../services/product/product.service';
import {Globals} from '../../../global/globals';
import {CartGlobals} from '../../../global/cartGlobals';
import {CartItem} from '../../../dtos/cartItem';
import {CartService} from '../../../services/cart.service';
import {filter} from 'rxjs/operators';
import {Observable, Subscription} from 'rxjs';

@Component({
  selector: 'app-shop-product-details',
  templateUrl: './shop-product-details.component.html',
  styleUrls: ['./shop-product-details.component.scss']
})
export class ShopProductDetailsComponent implements OnInit, OnDestroy {
  product: Product;
  error = false;
  errorMessage = '';
  private urlSubscription: Subscription;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private productService: ProductService,
              private globals: Globals,
              private cartGlobals: CartGlobals,
              private cartService: CartService) {
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
    this.urlSubscription = this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
      const id = this.activatedRoute.snapshot.paramMap.get('id');
      this.fetchProduct(Number(id));
    });
  }

  ngOnDestroy() {
    this.urlSubscription.unsubscribe();
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
          this.cartGlobals.updateCartItem(item);

      }, (error) => {
        this.error = true;
        this.errorMessage = error;
      });

    } else {
      const cart  = this.cartGlobals.getCart();
      const quantity = Number(cart[this.cartGlobals.containsProductAtIndex(product)]['cartItemQuantity']) + 1;
      if (quantity <= 12) {
        cart[this.cartGlobals.containsProductAtIndex(product)]['cartItemQuantity'] = quantity;
        this.cartGlobals.updateCart(product, quantity);
        console.log('quantity ', quantity);
        const cartItem = new CartItem(product.id, quantity);
        cartItem.id = cart[this.cartGlobals.containsProductAtIndex(product)]['cartItemId'];
      this.cartService.updateToCart(cartItem).subscribe( (item) => {
        this.cartGlobals.updateCartItem(item);
      }, (error) => {
        this.error = true;
        this.errorMessage = error;
      });
      }
    }
    if (!this.error) {
      this.router.navigate(['cart']).then();
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  private fetchProduct(id: number) {
    this.productService.getProductById(id).subscribe((product) => {
      this.product = product;
    });
  }


}
