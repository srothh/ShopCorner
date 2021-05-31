import {Permissions} from './permissions.enum';

export class Operator {

  constructor(
    public id: number,
    public name: string,
    public loginName: string,
    public password: string,
    public email: string,
    public permissions: Permissions) {
  }

}
