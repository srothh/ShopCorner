import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/shop/home/home.component';
import {LoginComponent} from './components/shop/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/shop/message/message.component';
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

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'operator', component: OperatorComponent, children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {path: 'home', component: OperatorHomeComponent},
      {path: 'shop', component: OperatorShopComponent},
      {path: 'invoices', component: OperatorInvoiceComponent},
      {path: 'orders', component: OperatorOrderComponent},
      {path: 'products', component: OperatorProductComponent},
      {path: 'promotions', component: OperatorPromotionComponent},
      {path: 'statistics', component: OperatorStatisticComponent},
      {path: 'customers', component: OperatorCustomerComponent},
      {path: 'accounts', component: OperatorAccountComponent},
    ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
