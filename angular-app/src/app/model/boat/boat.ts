import { ICustomer } from 'src/app/model/customer';
import { IUser } from 'src/app/pages/registration/registration/user';
import { IAddress } from '../address';
import { IDateSpan } from '../dateSpan';
import { IGrade } from '../grade';
import { IImage } from '../image';
import { IBoatQuickReservation } from './boatQuickReservation';
import { IBoatReservation } from './boatReservation';

export interface IBoat {
  id: number;
  name: string;
  type: string;
  length: number;
  engineNumber: number;
  topSpeed: number;
  enginePower: number;
  address: IAddress;
  description: string;
  grade: number;
  boatRules: string;
  fishingEquipment: string[];
  pricePerHour: number;
  cancelCondition: string;
  capacity: number;
  boatImage: IImage[];
  boatReservation: IBoatReservation[];
  boatQuickReservation: IBoatQuickReservation[];
  availableReservationDateSpan: IDateSpan[];
  unavailableReservationDateSpan: IDateSpan[];
  subscribers: ICustomer[];
  boatOwner: IUser;
  averageGrade: number;
}

export interface IBoatPage {
  content: IBoat[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}

export const initBoat: IBoat = {
  id: 0,
  name: '',
  address: {
    city: '',
    country: '',
    latitude: 45.248007,
    longitude: 19.842518,
    street: '',
  },
  averageGrade: 0,
  description: '',
  grade: 0,
  pricePerHour: 0,
  type: '',
  length: 0,
  capacity: 0,
  engineNumber: 0,
  topSpeed: 0,
  enginePower: 0,
  fishingEquipment: [],
  cancelCondition: '',
  boatRules: '',
  boatImage: [],
  boatReservation: [],
  boatQuickReservation: [],
  availableReservationDateSpan: [],
  unavailableReservationDateSpan: [],
  subscribers: [],
  boatOwner: {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    roles: [],
    address: {
      street: '',
      city: '',
      country: '',
      latitude: 0,
      longitude: 0,
    },
  },
};
