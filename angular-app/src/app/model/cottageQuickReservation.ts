import { ICottage, initCottage } from './cottage';
import { IDateSpan } from './dateSpan';

export interface ICottageQuickReservation {
  id: number;
  duration: IDateSpan;
  guestCapacity: number;
  price: number;
  cottage: ICottage;
}

export interface ICottageQuickReservationPage {
  id: number;
  duration: IDateSpan;
  guestCapacity: number;
  price: number;
  cottage: ICottage;
}

export interface ICottageQuickReservationPage {
  content: ICottageQuickReservation[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}

export const emptyCottageQuickReservation: ICottageQuickReservation = {
  id: 0,
  duration: {
    startDate: new Date(),
    endDate: new Date(),
  },
  guestCapacity: 0,
  price: 0,
  cottage: initCottage,
};
