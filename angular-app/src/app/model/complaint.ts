import { emptyBoatReservation, IBoatReservation } from './boat/boatReservation';
import {
  emptyCottageReservation,
  ICottageReservation,
} from './cottageReservation';
import {
  emptyFishingReservation,
  IFishingReservation,
} from './fishingReservation';

export interface IComplaint {
  id: number;
  cottageReservation: ICottageReservation | null;
  boatReservation: IBoatReservation | null;
  fishingReservation: IFishingReservation | null;
  text: string;
  answerOwner: string;
  answerCustomer: string;
}

export const emptyComplaint: IComplaint = {
  id: 0,
  text: '',
  cottageReservation: null,
  boatReservation: null,
  fishingReservation: null,
  answerOwner: '',
  answerCustomer: '',
};
