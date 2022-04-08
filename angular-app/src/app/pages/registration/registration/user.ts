import { StringMap } from '@angular/compiler/src/compiler_facade_interface';

export interface IUser {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
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
}