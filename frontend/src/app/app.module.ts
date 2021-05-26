import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/shop/shop-header/header.component';
import {FooterComponent} from './components/shop/shop-footer/footer.component';
import {ShopHomeComponent} from './components/shop/shop-home/shop-home.component';
import {ShopLoginComponent} from './components/shop/shop-login/shop-login.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { OperatorComponent } from './components/operator/operator.component';
import { OperatorHomeComponent } from './components/operator/operator-home/operator-home.component';
import { OperatorShopComponent } from './components/operator/operator-shop/operator-shop.component';
import { OperatorInvoiceComponent } from './components/operator/operator-invoice/operator-invoice.component';
import { OperatorOrderComponent } from './components/operator/operator-order/operator-order.component';
import { OperatorProductComponent } from './components/operator/operator-products/operator-product.component';
import { OperatorPromotionComponent } from './components/operator/operator-promotions/operator-promotion.component';
import { OperatorStatisticComponent } from './components/operator/operator-statistics/operator-statistic.component';
import { OperatorCustomerComponent } from './components/operator/operator-customers/operator-customer.component';
import { OperatorAccountComponent } from './components/operator/operator-accounts/operator-account.component';
import { ShopComponent } from './components/shop/shop.component';
import { ShopProductComponent } from './components/shop/shop-product/shop-product.component';
import { ShopCartComponent } from './components/shop/shop-cart/shop-cart.component';
import { OperatorRegistrationComponent } from './components/operator/operator-registration/operator-registration.component';
import { OperatorAddProductComponent } from './components/operator/operator-add-product/operator-add-product.component';
import {ShopRegistrationComponent} from './components/shop/shop-registration/shop-registration.component';
import {ShopAccountComponent} from './components/shop/shop-account/shop-account.component';
import { OperatorProductDetailsComponent } from './components/operator/operator-product-details/operator-product-details.component';
import { OperatorProductFormComponent } from './components/operator/operator-product-form/operator-product-form.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ShopHomeComponent,
    ShopLoginComponent,
    ShopAccountComponent,
    OperatorComponent,
    OperatorHomeComponent,
    OperatorShopComponent,
    OperatorInvoiceComponent,
    OperatorOrderComponent,
    OperatorProductComponent,
    OperatorPromotionComponent,
    OperatorStatisticComponent,
    OperatorCustomerComponent,
    OperatorAccountComponent,
    ShopComponent,
    ShopProductComponent,
    ShopCartComponent,
    OperatorRegistrationComponent,
    OperatorAddProductComponent,
    ShopRegistrationComponent,
    OperatorProductDetailsComponent,
    OperatorProductFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
