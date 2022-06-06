import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IDateSpan } from '../dateSpan';

export interface IBoatQuickReservation {
  id: number;
  duration: IDateSpan;
  guestCapacity: number;
  price: number;
  boat: IBoat;
}

export interface IBoatQuickReservationPage {
  content: IBoatQuickReservation[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}

export const emptyBoatQuickReservation: IBoatQuickReservation = {
  id: 0,
  duration: {
    startDate: new Date(),
    endDate: new Date(),
  },
  guestCapacity: 0,
  price: 0,
  boat: initBoat,
};
