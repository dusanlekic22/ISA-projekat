import { emptyBoatReservation } from './boat/boatReservation';
import { emptyCottageReservation } from './cottageReservation';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { IBoat } from './boat/boat';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import {
  IFishingReservation,
  emptyFishingReservation,
} from './fishingReservation';

export interface IReview {
  customerId: number;

  text: string;

  ownerGrade: number;

  entityGrade: number;

  cottageReservation: ICottageReservation;

  boatReservation: IBoatReservation;

  fishingReservation: IFishingReservation;
}
export const emptyReview = {
  customerId: 0,
  text: '',
  ownerGrade: 0,
  entityGrade: 0,
  cottageReservation: emptyCottageReservation,
  boatReservation: emptyBoatReservation,
  fishingReservation: emptyFishingReservation,
};
