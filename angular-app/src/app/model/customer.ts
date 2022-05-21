import { IRole } from './../pages/registration/registration/user';
import { IAddress } from './address';

export interface ICustomer {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  enabled: boolean;
  verificationCode: string;
  roles: IRole[];
  phoneNumber: string;
  address: IAddress;
  points: string;
  loyalityProgram: string;
}
