import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ShopService} from '../../../services/shop.service';
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
      this.settingsFormGroup.controls['imageSource'].setValue(loadEvent.target.result);
    });
  }

  loadSettings() {
    this.shopService.loadSettings().subscribe((shopSettings) => {
      this.loadShopSettingsIntoForm(shopSettings);
    }, error => this.handleError(error));
  }

  updateSettings() {
    const title = this.settingsFormGroup.controls['title'].value;
    const logo = this.settingsFormGroup.controls['imageSource'].value;
    const bannerTitle = this.settingsFormGroup.controls['bannerTitle'].value;
    const bannerBody = this.settingsFormGroup.controls['bannerText'].value;
    const shopSettings = new ShopSettings(title, logo, bannerTitle, bannerBody);
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
    this.settingsFormGroup.controls['title'].setValue(shopSettings.title);
    this.settingsFormGroup.controls['imageSource'].setValue(shopSettings.logo);
    this.settingsFormGroup.controls['bannerTitle'].setValue(shopSettings.bannerTitle);
    this.settingsFormGroup.controls['bannerText'].setValue(shopSettings.bannerText);
  }

  private initializeForm() {
    this.settingsFormGroup = this.formBuilder.group({
      title: [''],
      imageSource: [''],
      bannerTitle: [''],
      bannerText: [''],
    });
  }

}
