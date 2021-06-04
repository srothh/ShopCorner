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
import {OperatorInvoiceFormComponent} from './components/operator/operator-invoice-form/operator-invoice-form.component';
import {OperatorInvoiceDetailviewComponent} from './components/operator/operator-invoice-detailview/operator-invoice-detailview.component';
import {OperatorCategoriesComponent} from './components/operator/operator-categories/operator-categories.component';
import {OperatorAddCategoryComponent} from './components/operator/operator-add-category/operator-add-category.component';
import {OperatorAdminGuard} from './guards/operator-admin.guard';

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: ShopHomeComponent},
      {path: 'login', canActivate: [PreventCustomerLoginGuard], component: ShopLoginComponent},
      {path: 'products', component: ShopProductComponent},
      {path: 'account', canActivate: [CustomerAuthGuard], component: ShopAccountComponent},
      {path: 'cart', component: ShopCartComponent},
      {path: 'register', component: ShopRegistrationComponent}
    ]
  },
  {
    path: 'operator', canActivate: [OperatorAuthGuard], component: OperatorComponent, children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: 'home', component: OperatorHomeComponent},
      {path: 'shop', canActivate: [OperatorAdminGuard], component: OperatorShopComponent},
      {path: 'invoices', component: OperatorInvoiceComponent},
      {path: 'categories', canActivate: [OperatorAdminGuard], component: OperatorCategoriesComponent},
      {path: 'categories/add', canActivate: [OperatorAdminGuard], component: OperatorAddCategoryComponent},
      {path: 'orders', component: OperatorOrderComponent},
      {path: 'products', canActivate: [OperatorAdminGuard], component: OperatorProductComponent},
      {path: 'products/add', canActivate: [OperatorAdminGuard], component: OperatorAddProductComponent},
      {path: 'products/:id', canActivate: [OperatorAdminGuard], component: OperatorProductDetailsComponent},
      {path: 'promotions', component: OperatorPromotionComponent},
      {path: 'statistics', component: OperatorStatisticComponent},
      {path: 'customers', component: OperatorCustomerComponent},
      {path: 'accounts', component: OperatorAccountComponent},
      {path: 'registration', canActivate: [OperatorAdminGuard],  component: OperatorRegistrationComponent},
      {path: 'account/edit', component: OperatorEditAccountComponent},
    ],
  },
  {path: 'operator/login', canActivate: [PreventOperatorLoginGuard], component: OperatorLoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
