import { IRole, IUser } from './../pages/registration/registration/user';
import { IAddress } from './address';
import { ILoyaltyProgram, LoyaltyRank } from './loyaltyProgram';

export interface ICustomer extends IUser {
  loyaltyProgram: ILoyaltyProgram;
  penalties: number;
  enabled: boolean;
  verificationCode: string;
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
  penalties: 0,
  deleted: false,
};
