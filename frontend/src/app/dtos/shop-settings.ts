import {FormGroup} from '@angular/forms';

export class ShopSettings {
  static readonly formKey = {
    title: 'title',
    logo: 'logo',
    bannerTitle: 'bannerTitle',
    bannerText: 'bannerText',
    street: 'street',
    houseNumber: 'houseNumber',
    stairNumber: 'stairNumber',
    doorNumber: 'doorNumber',
    postalCode: 'postalCode',
    city: 'city',
    phoneNumber: 'phoneNumber',
    email: 'email',
  };

  constructor(
    public title: string,
    public logo: string,
    public bannerTitle: string,
    public bannerText: string,
    public street: string,
    public houseNumber: string,
    public stairNumber: number,
    public doorNumber: string,
    public postalCode: number,
    public city: string,
    public phoneNumber: string,
    public email: string,
  ) {
  }

  static buildFromFormGroup(formGroup): ShopSettings {
    const controls = formGroup.controls;
    const key = ShopSettings.formKey;

    const title = controls[key.title].value;
    const logo = controls[key.logo].value;
    const bannerTitle = controls[key.bannerTitle].value;
    const bannerBody = controls[key.bannerText].value;
    const street = controls[key.street].value;
    const houseNumber = controls[key.houseNumber].value;
    const stairNumber = controls[key.stairNumber].value;
    const doorNumber = controls[key.doorNumber].value;
    const postalCode = controls[key.postalCode].value;
    const city = controls[key.city].value;
    const phoneNumber = controls[key.phoneNumber].value;
    const email = controls[key.email].value;

    return new ShopSettings(
      title, logo,
      bannerTitle, bannerBody,
      street, houseNumber,
      stairNumber, doorNumber,
      postalCode, city,
      phoneNumber, email
    );
  }

  buildFormGroup(formGroup): FormGroup {
    const controls = formGroup.controls;
    const key = ShopSettings.formKey;

    controls[key.title].setValue(this.title);
    controls[key.logo].setValue(this.logo);
    controls[key.bannerTitle].setValue(this.bannerTitle);
    controls[key.bannerText].setValue(this.bannerText);
    controls[key.street].setValue(this.street);
    controls[key.houseNumber].setValue(this.houseNumber);
    controls[key.stairNumber].setValue(this.stairNumber);
    controls[key.doorNumber].setValue(this.doorNumber);
    controls[key.postalCode].setValue(this.postalCode);
    controls[key.city].setValue(this.city);
    controls[key.phoneNumber].setValue(this.phoneNumber);
    controls[key.email].setValue(this.email);

    return formGroup;
  }
}
