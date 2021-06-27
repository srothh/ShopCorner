import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ShopHomeComponent} from './components/shop/shop-home/shop-home.component';
import {ShopLoginComponent} from './components/shop/shop-login/shop-login.component';
import {OperatorComponent} from './components/operator/operator.component';
import {OperatorHomeComponent} from './components/operator/operator-home/operator-home.component';
import {OperatorShopComponent} from './components/operator/operator-shop/operator-shop.component';
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
import {CustomerAuthGuard} from './guards/customer-auth.guard';
import {ShopAccountComponent} from './components/shop/shop-account/shop-account.component';
import {OperatorProductDetailsComponent} from './components/operator/operator-product-details/operator-product-details.component';
import {PreventCustomerLoginGuard} from './guards/prevent-customer-login.guard';
import {OperatorAuthGuard} from './guards/operator-auth.guard';
import {PreventOperatorLoginGuard} from './guards/prevent-operator-login.guard';
import {OperatorLoginComponent} from './components/operator/operator-login/operator-login.component';
import {OperatorEditAccountComponent} from './components/operator/operator-edit-account/operator-edit-account.component';
import {OperatorInvoiceComponent} from './components/operator/operator-invoice/operator-invoice.component';
import {OperatorCategoriesComponent} from './components/operator/operator-categories/operator-categories.component';
import {OperatorAddCategoryComponent} from './components/operator/operator-add-category/operator-add-category.component';
import {ShopProductDetailsComponent} from './components/shop/shop-product-details/shop-product-details.component';
import {ShopAccountProfileComponent} from './components/shop/shop-account-profile/shop-account-profile.component';
import {ShopAccountOrdersComponent} from './components/shop/shop-account-orders/shop-account-orders.component';
import {OperatorAdminGuard} from './guards/operator-admin.guard';
import {ShopCheckoutComponent} from './components/shop/shop-checkout/shop-checkout.component';
import {OperatorCategoryDetailsComponent} from './components/operator/operator-category-details/operator-category-details.component';
import {ShopOrderSuccessComponent} from './components/shop/shop-order-success/shop-order-success.component';
import {OrderSuccessGuard} from './guards/order-success.guard';
import {OperatorOrderSettingsComponent} from './components/operator/operator-order-settings/operator-order-settings.component';
import {ShopAccountOrderDetailsComponent} from './components/shop/shop-account-order-details/shop-account-order-details.component';
import {ShopNotFoundComponent} from './components/shop/shop-not-found/shop-not-found.component';

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: ShopHomeComponent},
      {path: 'login', canActivate: [PreventCustomerLoginGuard], component: ShopLoginComponent},
      {path: 'products', component: ShopProductComponent},
      {path: 'products/:id', component: ShopProductDetailsComponent},
      {path: 'account', canActivate: [CustomerAuthGuard], component: ShopAccountComponent},
      {path: 'account/profile', canActivate: [CustomerAuthGuard], component: ShopAccountProfileComponent},
      {path: 'account/orders', canActivate: [CustomerAuthGuard], component: ShopAccountOrdersComponent},
      {path: 'account/orders/:id', canActivate: [CustomerAuthGuard], component: ShopAccountOrderDetailsComponent},
      {path: 'cart', component: ShopCartComponent},
      {path: 'register', component: ShopRegistrationComponent},
      {path: 'order-success', canActivate: [OrderSuccessGuard], component: ShopOrderSuccessComponent},
      {path: 'register', component: ShopRegistrationComponent},
      {path: 'checkout', canActivate: [CustomerAuthGuard], component: ShopCheckoutComponent},
    ],
  },
  {
    path: 'operator', canActivate: [OperatorAuthGuard], component: OperatorComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: OperatorHomeComponent},
      {path: 'shop', canActivate: [OperatorAdminGuard], component: OperatorShopComponent},
      {path: 'invoices', component: OperatorInvoiceComponent},
      {path: 'categories', component: OperatorCategoriesComponent},
      {path: 'categories/add', canActivate: [OperatorAdminGuard], component: OperatorAddCategoryComponent},
      {path: 'categories/:id', component: OperatorCategoryDetailsComponent},
      {path: 'orders', component: OperatorOrderComponent},
      {path: 'orders/settings', component: OperatorOrderSettingsComponent},
      {path: 'products', component: OperatorProductComponent},
      {path: 'products/add', canActivate: [OperatorAdminGuard], component: OperatorAddProductComponent},
      {path: 'products/:id', component: OperatorProductDetailsComponent},
      {path: 'promotions', component: OperatorPromotionComponent},
      {path: 'statistics', component: OperatorStatisticComponent},
      {path: 'customers', component: OperatorCustomerComponent},
      {path: 'accounts', component: OperatorAccountComponent},
      {path: 'registration', canActivate: [OperatorAdminGuard], component: OperatorRegistrationComponent},
      {path: 'account/edit', component: OperatorEditAccountComponent},
    ],
  },
  {path: 'operator/login', canActivate: [PreventOperatorLoginGuard], component: OperatorLoginComponent},
  {path: '404', component: ShopNotFoundComponent},
  {path: '**', redirectTo: '/404'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
