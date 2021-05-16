import {Address} from './address';

export class Customer {
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
}
