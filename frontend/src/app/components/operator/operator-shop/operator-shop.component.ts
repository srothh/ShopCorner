import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ShopService} from '../../../services/shop/shop.service';
import {ShopSettings} from '../../../dtos/shop-settings';

@Component({
  selector: 'app-operator-shop',
  templateUrl: './operator-shop.component.html',
  styleUrls: ['./operator-shop.component.scss']
})
export class OperatorShopComponent implements OnInit {
  settingsFormGroup: FormGroup;

  error: boolean;
  errorMessage: string;
  successfulUpdate: boolean;

  constructor(private formBuilder: FormBuilder, private shopService: ShopService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.loadSettings();
  }

  selectFile(event) {
    const fileToUpload = event.target.files.item(0);
    const reader = new FileReader();
    reader.readAsDataURL(fileToUpload);
    reader.onload = ((loadEvent) => {
      // fileSource should be string in a base64-encoded format
      this.settingsFormGroup.controls[ShopSettings.formKey.logo].setValue(loadEvent.target.result);
    });
  }

  loadSettings() {
    this.shopService.loadSettings().subscribe((shopSettings) => {
      this.loadShopSettingsIntoForm(shopSettings);
    }, error => this.handleError(error));
  }

  updateSettings() {
    const shopSettings = ShopSettings.buildFromFormGroup(this.settingsFormGroup);
    this.shopService.updateSettings(shopSettings).subscribe(() => {
      this.successfulUpdate = true;
    }, error => this.handleError(error));
  }

  vanishSuccess() {
    this.successfulUpdate = false;
  }

  vanishError() {
    this.error = false;
    this.errorMessage = '';
  }

  private handleError(error) {
    this.error = true;
    this.errorMessage = error;
  }

  private loadShopSettingsIntoForm(shopSettings: ShopSettings) {
    this.settingsFormGroup = shopSettings.buildFormGroup(this.settingsFormGroup);
  }

  private initializeForm() {
    this.settingsFormGroup = this.formBuilder.group({
      title: [],
      logo: [],
      bannerTitle: [],
      bannerText: [],
      street: [],
      houseNumber: [],
      stairNumber: [],
      doorNumber: [],
      postalCode: [],
      city: [],
      phoneNumber: [],
      email: [],
    });
  }

}
