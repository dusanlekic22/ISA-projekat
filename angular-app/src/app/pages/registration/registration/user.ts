import { StringMap } from '@angular/compiler/src/compiler_facade_interface';
import { IAddress } from 'src/app/model/address';

export interface IUser {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  roles: IRole[];
  address: IAddress;
}

export interface IDirective {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  roles: IRole[];
  activeRoles: Array<string>;
}

export interface IUserLogin {
  username: string;
  password: string;
}

export interface IToken {
  accessToken: string;
  expiresIn: number;
}

export interface IRole {
  id: number;
  name: string;
}
