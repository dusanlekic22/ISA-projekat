import { StringMap } from '@angular/compiler/src/compiler_facade_interface';

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

export interface IAddress {
  street: string;
  city: string;
  country: string;
  latitude: string;
  longitude: string;
}

export interface IRole {
  id: number;
  name: string;
}
