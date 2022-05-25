import { IUser } from '../pages/registration/registration/user';
import { IAddress } from './address';
import { ICottageImage } from './cottageImage';
import { ICottageQuickReservation } from './cottageQuickReservation';
import { ICottageReservation } from './cottageReservation';
import { IDateSpan } from './dateSpan';

export interface ICottage {
  id: number;
  name: string;
  address: IAddress;
  promoDescription: string;
  bedCount: number;
  roomCount: number;
  cottageRules: string;
  pricePerHour: number;
  cottageImage: ICottageImage[];
  cottageReservation: ICottageReservation[];
  cottageQuickReservation: ICottageQuickReservation[];
  availableReservationDateSpan: IDateSpan[];
  cottageOwner: IUser;
}
