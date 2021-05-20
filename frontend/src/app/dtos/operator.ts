import {Permissions} from './permissions.enum';

export class Operator {
  constructor(
    public id: number,
    public loginName: string,
    public password: string,
    public name: string,
    public email: string,
    public permissions: Permissions
  ) {
  }
}
