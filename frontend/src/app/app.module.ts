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
import {OperatorComponent} from './components/operator/operator.component';
import {OperatorHomeComponent} from './components/operator/operator-home/operator-home.component';
import {OperatorShopComponent} from './components/operator/operator-shop/operator-shop.component';
import {OperatorInvoiceFormComponent} from './components/operator/operator-invoice-form/operator-invoice-form.component';
import {OperatorOrderComponent} from './components/operator/operator-order/operator-order.component';
import {OperatorProductComponent} from './components/operator/operator-products/operator-product.component';
import {OperatorPromotionComponent} from './components/operator/operator-promotions/operator-promotion.component';
import {OperatorStatisticComponent} from './components/operator/operator-statistics/operator-statistic.component';
import {OperatorCustomerComponent} from './components/operator/operator-customers/operator-customer.component';
import {OperatorAccountComponent} from './components/operator/operator-accounts/operator-account.component';
import {ShopComponent} from './components/shop/shop.component';
import {ShopProductComponent} from './components/shop/shop-product/shop-product.component';
import {ShopCartComponent} from './components/shop/shop-cart/shop-cart.component';
import {OperatorRegistrationComponent} from './components/operator/operator-registration/operator-registration.component';
import {OperatorAddProductComponent} from './components/operator/operator-add-product/operator-add-product.component';
import {ShopRegistrationComponent} from './components/shop/shop-registration/shop-registration.component';
import {ShopAccountComponent} from './components/shop/shop-account/shop-account.component';
import {OperatorEditAccountComponent} from './components/operator/operator-edit-account/operator-edit-account.component';
import {OperatorProductDetailsComponent} from './components/operator/operator-product-details/operator-product-details.component';
import {OperatorProductFormComponent} from './components/operator/operator-product-form/operator-product-form.component';
import {BaseLoginComponent} from './components/common/base-login/base-login.component';
import {OperatorLoginComponent} from './components/operator/operator-login/operator-login.component';
import {OperatorInvoiceComponent} from './components/operator/operator-invoice/operator-invoice.component';
import {OperatorInvoiceDetailViewComponent} from './components/operator/operator-invoice-detailView/operator-invoice-detailView.component';
import {OperatorCategoriesComponent} from './components/operator/operator-categories/operator-categories.component';
import {OperatorAddCategoryComponent} from './components/operator/operator-add-category/operator-add-category.component';
import {BaseShopProductItemComponent} from './components/common/base-shop-product-item/base-shop-product-item.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ShopProductDetailsComponent} from './components/shop/shop-product-details/shop-product-details.component';
import {ShopAccountOrdersComponent} from './components/shop/shop-account-orders/shop-account-orders.component';
import {ShopAccountProfileComponent} from './components/shop/shop-account-profile/shop-account-profile.component';
import { BaseProductSearchBarComponent } from './components/common/base-product-search-bar/base-product-search-bar.component';
import {NgbdModalInfoComponent} from './components/common/ngbd-modal-info/ngbd-modal-info.component';
import {NgdbModalActionComponent} from './components/common/ngbd-modal-action/ngdb-modal-action.component';
import { OperatorCategoryDetailsComponent } from './components/operator/operator-category-details/operator-category-details.component';
import { OperatorCategoryFormComponent } from './components/operator/operator-category-form/operator-category-form.component';
import { ShopOrderSuccessComponent } from './components/shop/shop-order-success/shop-order-success.component';
import { OperatorPromotionFormComponent } from './components/operator/operator-promotion-form/operator-promotion-form.component';
import { ShopCheckoutComponent } from './components/shop/shop-checkout/shop-checkout.component';
import { OperatorOrderSettingsComponent } from './components/operator/operator-order-settings/operator-order-settings.component';
import { BaseShopOrdersHeaderComponent } from './components/common/base-shop-orders-header/base-shop-orders-header.component';
import { BaseShopOrderItemComponent } from './components/common/base-shop-order-item/base-shop-order-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ShopHomeComponent,
    ShopLoginComponent,
    BaseLoginComponent,
    ShopAccountComponent,
    OperatorLoginComponent,
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
    OperatorPromotionFormComponent,
    OperatorAddProductComponent,
    ShopRegistrationComponent,
    OperatorEditAccountComponent,
    OperatorProductFormComponent,
    OperatorProductDetailsComponent,
    OperatorInvoiceFormComponent,
    OperatorInvoiceComponent,
    OperatorInvoiceDetailViewComponent,
    BaseShopProductItemComponent,
    OperatorCategoriesComponent,
    OperatorAddCategoryComponent,
    BaseShopProductItemComponent,
    ShopProductDetailsComponent,
    ShopAccountOrdersComponent,
    ShopAccountProfileComponent,
    BaseProductSearchBarComponent,
    NgbdModalInfoComponent,
    NgdbModalActionComponent,
    ShopAccountProfileComponent,
    OperatorCategoryDetailsComponent,
    OperatorCategoryFormComponent,
    ShopOrderSuccessComponent,
    OperatorCategoryFormComponent,
    ShopCheckoutComponent,
    OperatorOrderSettingsComponent,
    BaseShopOrdersHeaderComponent,
    BaseShopOrderItemComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    FontAwesomeModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
