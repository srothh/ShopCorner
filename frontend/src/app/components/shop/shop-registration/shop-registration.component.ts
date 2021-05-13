import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';
import {Address} from '../../../dtos/address';
import {Customer} from '../../../dtos/customer';
import {AddressService} from '../../../services/address.service';
import {CustomerService} from '../../../services/customer.service';


@Component({
  selector: 'app-shop-registration',
  templateUrl: './shop-registration.component.html',
  styleUrls: ['./shop-registration.component.scss']
})
export class ShopRegistrationComponent {

  registrationForm: FormGroup;
  addressForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  error = false;
  errorMessage = '';
  addId: number;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,
              private addressService: AddressService, private customerService: CustomerService) {
    this.registrationForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      loginName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      name: ['', [Validators.required]],
      phoneNumber: ['']
    });
    this.addressForm = this.formBuilder.group({
      street: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      houseNumber: ['', [Validators.required]],
      stairNumber: [''],
      doorNumber: ['']
    });
  }

  registerUser() {
    this.submitted = true;
    if (this.registrationForm.valid && this.addressForm.valid) {
      const address: Address = new Address(0,this.addressForm.controls.street.value,
        this.addressForm.controls.postalCode.value, this.addressForm.controls.houseNumber.value,
        this.addressForm.controls.stairNumber.value, this.addressForm.controls.doorNumber.value);
      this.addressService.addAddress(address).subscribe((add: Address) => {
        const customer: Customer = new Customer(0, this.registrationForm.controls.loginName.value,
          this.registrationForm.controls.password.value,
          this.registrationForm.controls.name.value, this.registrationForm.controls.email.value, 0,
          this.registrationForm.controls.phoneNumber.value);
        this.customerService.addCustomer(customer,add.id).subscribe(() => {
          },
          error1 => {
            console.log(error1);
          }
        );
      }, error=> {
console.log(error);
}
    );
    } else {
      console.log('Invalid input');
    }
  }
}
