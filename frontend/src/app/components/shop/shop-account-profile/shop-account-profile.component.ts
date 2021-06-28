import {Component, OnInit} from '@angular/core';
import {faEdit, faMinusCircle} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Customer} from '../../../dtos/customer';
import {MeService} from '../../../services/me.service';
import {Address} from '../../../dtos/address';
import {CustomerAuthService} from '../../../services/auth/customer-auth.service';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgbdModalInfoComponent} from '../../common/ngbd-modal-info/ngbd-modal-info.component';
import {NgdbModalActionComponent} from '../../common/ngbd-modal-action/ngdb-modal-action.component';

@Component({
  selector: 'app-shop-account-profile',
  templateUrl: './shop-account-profile.component.html',
  styleUrls: ['./shop-account-profile.component.scss']
})
export class ShopAccountProfileComponent implements OnInit {
  // Fontawesome Styling Components
  faEdit = faEdit;
  faMinusCircle = faMinusCircle;

  editForm: FormGroup;
  isEditMode = false;
  myProfile: Customer;

  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder,
              private meService: MeService,
              private customerAuthService: CustomerAuthService,
              private router: Router,
              private modalService: NgbModal) {
    this.initializeForm();
  }

  ngOnInit(): void {
    this.fetchMyProfile();
  }

  /**
   * Opens the modal and asks the user if they really want to delete the account
   */
  attemptToDeleteAccount() {
    const modalRef = this.modalService.open(NgdbModalActionComponent);
    modalRef.componentInstance.title = 'Warnung';
    modalRef.componentInstance.body = 'Wollen Sie Ihr Konto unwiderruflich löschen?';
    modalRef.componentInstance.actionButtonTitle = 'Löschen';
    modalRef.componentInstance.actionButtonStyle = 'danger';
    modalRef.componentInstance.action = () => {
      this.deleteAccount();
    };
  }

  /**
   * Removes the current logged in user account from the database
   */
  deleteAccount() {
    this.meService.deleteMyAccount().subscribe(() => {
      this.customerAuthService.logoutUser();
      this.router.navigate(['/home']).then(() => {
        const modalRef = this.modalService.open(NgbdModalInfoComponent);
        modalRef.componentInstance.title = 'Erfolgreich!';
        modalRef.componentInstance.body = 'Ihr Konto wurde erfolgreich gelöscht!';
      });
    }, error => {
      this.error = true;
      this.errorMessage = error;
    });
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
      'standard',
      this.editForm.value.name,
      this.editForm.value.email,
      address,
      this.editForm.value.phoneNumber
    );

    if(this.editForm.value.password !== '' && this.editForm.value.newPassword !== ''){
      this.meService.updatePassword(this.editForm.value.password, this.editForm.value.newPassword).subscribe(() => {}, error => {
        this.error = true;
        this.errorMessage = error;
      }, ()=> {
        this.editData();
      });
    } else {
      this.editData();
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Fetches my profile (customer data) and initializes the form
   *
   * @private
   */
  private fetchMyProfile() {
    this.meService.getMyProfileData().subscribe((myProfile) => {
      this.myProfile = myProfile;
      this.updateForm();
    }, error => {
      this.error = true;
      this.errorMessage = error;
    });
  }

  /**
   * Updates user data not related to the password.
   *
   * @private
   */
  private editData(){
    const user = this.customerAuthService.getUser();

    this.meService.updateProfileData(this.myProfile).subscribe(() => {
    }, error => {
      this.error = true;
      this.errorMessage = error;
    }, () => {
      if (this.myProfile.loginName !== user) {
        this.customerAuthService.logoutUser();
        this.router.navigate(['/login']);
      }
    });
  }

  private updateForm() {
    this.editForm.value.loginName = this.myProfile.loginName;
    this.editForm.value.email = this.myProfile.email;
    this.editForm.value.name = this.myProfile.name;
    this.editForm.value.phoneNumber = this.myProfile.phoneNumber;
    this.editForm.value.street = this.myProfile.loginName;
    this.editForm.value.postalCode = this.myProfile.address.postalCode;
    this.editForm.value.houseNumber = this.myProfile.address.houseNumber;
    this.editForm.value.stairNumber = this.myProfile.address.stairNumber;
    this.editForm.value.doorNumber = this.myProfile.address.doorNumber;
  }

  /**
   * Initializes the form data with the customer data
   *
   * @private
   */
  private initializeForm() {
    this.editForm = this.formBuilder.group({
      loginName: [],
      email: [],
      name: [],
      phoneNumber: [],
      password: [],
      newPassword: [''],
      street: [],
      postalCode: [],
      houseNumber: [],
      stairNumber: [],
      doorNumber: [],
    });
  }
}
