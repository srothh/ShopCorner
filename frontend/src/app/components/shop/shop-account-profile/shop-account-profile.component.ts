import {Component, OnInit} from '@angular/core';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Customer} from '../../../dtos/customer';

@Component({
  selector: 'app-shop-account-profile',
  templateUrl: './shop-account-profile.component.html',
  styleUrls: ['./shop-account-profile.component.scss']
})
export class ShopAccountProfileComponent implements OnInit {
  // Fontawesome Styling Components
  faEdit = faEdit;

  editForm: FormGroup;
  isEditMode = false;
  customer: Customer;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    // TODO: fetch the customer and assign it here
    this.customer = null;
    this.initializeForm();
  }

  /**
   * Toggles between edit mode
   */
  toggleEditMode() {
    this.isEditMode = !this.isEditMode;
  }

  /**
   * Cancels the edit process by reassigning data from the non-edited customer back to the form
   */
  cancelEdit() {
    this.toggleEditMode();
    this.initializeForm();
  }

  /**
   * Saves the edited user to the database
   */
  saveUser() {
    this.toggleEditMode();
  }

  /**
   * Initializes the form data with the customer data
   *
   * @private
   */
  private initializeForm() {
    // TODO: add validators
    this.editForm = this.formBuilder.group({
      loginName: [this.customer.loginName],
      email: [this.customer.email],
      name: [this.customer.name],
      phoneNumber: [this.customer.phoneNumber],
      password: [''],
      street: [this.customer.address.street],
      postalCode: [this.customer.address.postalCode],
      houseNumber: [this.customer.address.houseNumber],
      stairNumber: [this.customer.address.stairNumber],
      doorNumber: [this.customer.address.doorNumber],
    });
  }
}
