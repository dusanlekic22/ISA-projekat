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
