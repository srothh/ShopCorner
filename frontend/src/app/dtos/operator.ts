export class Operator {
  constructor(
    public id: number,
    public loginName: string,
    public password: string,
    public name: string,
    public email: string,
    public permissions: Permission
  ) {
  }
}

export enum Permission{
  admin = 'admin',
  employee = 'employee'
}
