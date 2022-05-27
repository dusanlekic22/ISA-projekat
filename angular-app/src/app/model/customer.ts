import { emptyFishingCourse } from './fishingCourse';
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

export const emptyCustomer: ICustomer = {
  id: 0,
  firstName: '',
  lastName: '',
  username: '',
  password: '',
  email: '',
  phoneNumber: '',
  roles: [],
  address: {
    street: '',
    city: '',
    country: '',
    latitude: '',
    longitude: '',
  },
  enabled: true,
  verificationCode: '',
  points: '',
  loyalityProgram: '',
};
