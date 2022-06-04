import { emptyFishingCourse } from './fishingCourse';
import { IRole } from './../pages/registration/registration/user';
import { IAddress } from './address';
import { ILoyaltyProgram, LoyaltyRank } from './loyaltyProgram';

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
  loyaltyProgram: ILoyaltyProgram;
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
    latitude: 0,
    longitude: 0,
  },
  enabled: true,
  verificationCode: '',
  loyaltyProgram: {
    loyaltyRank: LoyaltyRank.Regular,
    points: 0,
  },
};
