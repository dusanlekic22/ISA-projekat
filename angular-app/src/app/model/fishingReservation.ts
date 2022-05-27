import { emptyCustomer } from './customer';
import { ICustomer } from 'src/app/model/customer';
import { emptyFishingCourse } from './fishingCourse';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { IAddress } from './address';
import { IDateSpan } from './dateSpan';

export interface IFishingReservation {
  id: number;
  duration: IDateSpan;
  capacity: number;
  price: number;
  location: IAddress;
  customer: ICustomer;
  confirmed: boolean;
  fishingCourse: IFishingCourse;
}

export const emptyFishingReservation: IFishingReservation = {
  id: 0,
  duration: {
    startDate: new Date(),
    endDate: new Date(),
  },
  capacity: 0,
  price: 0,
  location: {
    street: '',
    city: '',
    country: '',
    latitude: '',
    longitude: '',
  },
  fishingCourse: emptyFishingCourse,
  confirmed: false,
  customer: emptyCustomer,
};
