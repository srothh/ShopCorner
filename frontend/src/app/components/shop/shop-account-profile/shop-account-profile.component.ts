import {Component, OnInit} from '@angular/core';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Customer} from '../../../dtos/customer';
import {MeService} from '../../../services/me.service';
import {Address} from '../../../dtos/address';

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
  myProfile: Customer;

  constructor(private formBuilder: FormBuilder, private meService: MeService) {
  }

  ngOnInit(): void {
    this.fetchMyProfile();
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
    const address = new Address(
      this.myProfile.id,
      this.editForm.value.street,
      this.editForm.value.postalCode,
      this.editForm.value.houseNumber,
      this.editForm.value.stairNumber,
      this.editForm.value.doorNumber
    );
    this.myProfile = new Customer(
      this.myProfile.id,
      this.editForm.value.loginName,
      this.editForm.value.password,
      this.editForm.value.name,
      this.editForm.value.email,
      address,
      this.editForm.value.phoneNumber
    );
    // TODO: save myProfile to the database, updates can be done on PUT /me
  }

  /**
   * Fetches my profile (customer data) and initializes the form
   * @private
   */
  private fetchMyProfile() {
    this.meService.getMyProfileData().subscribe((myProfile) => {
      this.myProfile = myProfile;
      this.initializeForm();
    });
  }

  /**
   * Initializes the form data with the customer data
   *
   * @private
   */
  private initializeForm() {
    // TODO: add validators
    this.editForm = this.formBuilder.group({
      loginName: [this.myProfile.loginName],
      email: [this.myProfile.email],
      name: [this.myProfile.name],
      phoneNumber: [this.myProfile.phoneNumber],
      password: [''],
      street: [this.myProfile.address.street],
      postalCode: [this.myProfile.address.postalCode],
      houseNumber: [this.myProfile.address.houseNumber],
      stairNumber: [this.myProfile.address.stairNumber],
      doorNumber: [this.myProfile.address.doorNumber],
    });
  }
}
