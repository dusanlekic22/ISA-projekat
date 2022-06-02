import { IBoat } from './boat';
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

export const emptyBoatAvailability = {
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
