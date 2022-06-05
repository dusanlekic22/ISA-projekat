import { ICottage, initCottage } from 'src/app/model/cottage';
import { IAdditionalService } from './additionalService';
import { ISortType } from './sortType';
import { ICustomer } from './customer';
import { IDateSpan } from './dateSpan';

export interface ICottageReservation {
  id: number;
  duration: IDateSpan;
  guestCapacity: number;
  price: number;
  customer: ICustomer;
  confirmed: boolean;
  cottage: ICottage;
}

export interface ICottageAvailability {
  name: string;
  dateSpan: IDateSpan;
  bedCapacity: number;
  price: number;
  grade: number;
  longitude: number;
  latitude: number;
  sortBy: ISortType[];
  freeAdditionalServices: string[];
}

export const emptyCottageReservation = {
  id: 0,
  duration: {
    startDate: new Date(),
    endDate: new Date(),
  },
  guestCapacity: 0,
  price: 0,
  customer: {
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
    points: '',
    loyalityProgram: '',
    penalties: 0,
  },
  confirmed: false,
  cottage: initCottage,
};

export const emptyCottageAvailability = {
  name: '',
  dateSpan: {
    startDate: new Date(),
    endDate: new Date(),
  },
  bedCapacity: 0,
  price: 0,
  grade: -1,
  longitude: 0,
  latitude: 0,
  sortBy: [],
  freeAdditionalServices: [],
};
