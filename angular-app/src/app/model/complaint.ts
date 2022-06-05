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
  cottageReservation: ICottageReservation | null;

  boatReservation: IBoatReservation | null;

  fishingReservation: IFishingReservation | null;

  text: string;
}

export const emptyComplaint = {
  text: '',
  cottageReservation: null,
  boatReservation: null,
  fishingReservation: null,
};
