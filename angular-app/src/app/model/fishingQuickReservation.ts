import { emptyFishingCourse } from './fishingCourse';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { IAddress } from './address';
import { IDateSpan } from './dateSpan';

export interface IFishingQuickReservation {
  id: number;
  duration: IDateSpan;
  capacity: number;
  price: number;
  location: IAddress;
  fishingCourse: IFishingCourse;
}

export const emptyFishingQuickReservation: IFishingQuickReservation = {
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
    latitude: 0,
    longitude: 0,
  },
  fishingCourse: emptyFishingCourse,
};
