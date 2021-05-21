import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ShopHomeComponent} from './components/shop/shop-home/shop-home.component';
import {ShopLoginComponent} from './components/shop/shop-login/shop-login.component';
import {OperatorComponent} from './components/operator/operator.component';
import {OperatorHomeComponent} from './components/operator/operator-home/operator-home.component';
import {OperatorShopComponent} from './components/operator/operator-shop/operator-shop.component';
import {OperatorInvoiceComponent} from './components/operator/operator-invoice/operator-invoice.component';
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
import {PreventLoginGuard} from './guards/prevent-login.guard';
import {OperatorEditAccountComponent} from "./components/operator/operator-edit-account/operator-edit-account.component";

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: ShopHomeComponent},
      {path: 'login', canActivate: [PreventLoginGuard], component: ShopLoginComponent},
      {path: 'products', component: ShopProductComponent},
      {path: 'account', canActivate: [CustomerAuthGuard], component: ShopAccountComponent},
      {path: 'cart', component: ShopCartComponent},
      {path: 'register', component: ShopRegistrationComponent}
    ]
  },
  {
    path: 'operator', component: OperatorComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: OperatorHomeComponent},
      {path: 'shop', component: OperatorShopComponent},
      {path: 'invoices', component: OperatorInvoiceComponent},
      {path: 'orders', component: OperatorOrderComponent},
      {path: 'products', component: OperatorProductComponent},
      {path: 'products/add', component: OperatorAddProductComponent},
      {path: 'promotions', component: OperatorPromotionComponent},
      {path: 'statistics', component: OperatorStatisticComponent},
      {path: 'customers', component: OperatorCustomerComponent},
      {path: 'accounts', component: OperatorAccountComponent},
      {path: 'registration', component: OperatorRegistrationComponent},
      {path: 'account/edit', component: OperatorEditAccountComponent},
    ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
