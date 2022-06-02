import { emptyCustomer } from 'src/app/model/customer';
import { initBoat } from 'src/app/model/boat/boat';
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

export const emptyBoatReservation : IBoatReservation = {
  id: 0,
  duration: {
    startDate: new Date(),
    endDate: new Date(),
  },
  guestCapacity: 0,
  price: 0,
  customer: emptyCustomer,
  confirmed: false,
  boat: initBoat,
};
