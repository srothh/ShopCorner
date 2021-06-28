import {Address} from './address';
import {FormGroup} from '@angular/forms';

export class Customer {
  static readonly formKey = {
    loginName: 'loginName',
    email: 'email',
    name: 'name',
    street: 'street',
    houseNumber: 'houseNumber',
    stairNumber: 'stairNumber',
    doorNumber: 'doorNumber',
    postalCode: 'postalCode',
    phoneNumber: 'phoneNumber',
  };

  constructor(
    public id: number,
    public loginName: string,
    public password: string,
    public name: string,
    public email: string,
    public address: Address,
    public phoneNumber: string
  ) {
  }

  buildFormGroup(formGroup): FormGroup {
    formGroup.controls[Customer.formKey.loginName].setValue(this.loginName);
    formGroup.controls[Customer.formKey.email].setValue(this.email);
    formGroup.controls[Customer.formKey.name].setValue(this.name);
    formGroup.controls[Customer.formKey.phoneNumber].setValue(this.phoneNumber);
    formGroup.controls[Customer.formKey.street].setValue(this.address.street);
    formGroup.controls[Customer.formKey.postalCode].setValue(this.address.postalCode);
    formGroup.controls[Customer.formKey.houseNumber].setValue(this.address.houseNumber);
    formGroup.controls[Customer.formKey.stairNumber].setValue(this.address.stairNumber);
    formGroup.controls[Customer.formKey.doorNumber].setValue(this.address.doorNumber);
    return formGroup;
  }
}
