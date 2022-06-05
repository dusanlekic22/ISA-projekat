import { emptyCustomer } from 'src/app/model/customer';
import { IBoat, initBoat } from './boat';
import { ICustomer } from '../customer';
import { IDateSpan } from '../dateSpan';
import { ISortType } from '../sortType';

export interface IBoatReservation {
  id: number;
  duration: IDateSpan;
  guestCapacity: number;
  price: number;
  customer: ICustomer;
  confirmed: boolean;
  boat: IBoat;
}

export interface IBoatAvailability {
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

// export const emptyBoatReservation: IBoatReservation = {
//   id: 0,
//   duration: {
//     startDate: new Date(),
//     endDate: new Date(),
//   },
//   guestCapacity: 0,
//   price: 0,
//   customer: emptyCustomer,
//   confirmed: false,
//   boat: initBoat,
// };

export const emptyBoatAvailability: IBoatAvailability = {
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

export const emptyBoatReservation: IBoatReservation = {
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
  boat: initBoat,
};
