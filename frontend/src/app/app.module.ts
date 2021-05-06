import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/shop/header/header.component';
import {FooterComponent} from './components/shop/footer/footer.component';
import {HomeComponent} from './components/shop/home/home.component';
import {LoginComponent} from './components/shop/login/login.component';
import {MessageComponent} from './components/shop/message/message.component';
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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
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
