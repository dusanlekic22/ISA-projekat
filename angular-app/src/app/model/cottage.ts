import { IGrade } from './grade';
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
  unavailableReservationDateSpan: IDateSpan[];
  cottageOwner: IUser;
  averageGrade:number;
  grades: IGrade[];
}

export interface ICottagePage {
  content: ICottage[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}
export const initCottage: ICottage = {
  id: 0,
  name: '',
  address: {
    city: '',
    country: '',
    latitude: 45.248007,
    longitude: 19.842518,
    street: '',
  },
  grades:[],
  averageGrade:0,
  promoDescription: '',
  bedCount: 0,
  roomCount: 0,
  pricePerHour: 0,
  cottageRules: '',
  cottageImage: [],
  cottageReservation: [],
  cottageQuickReservation: [],
  availableReservationDateSpan: [],
  unavailableReservationDateSpan: [],
  cottageOwner: {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: {
      street: '',
      city: '',
      country: '',
      latitude: 0,
      longitude: 0,
    },
    roles: [],
  },
};
