import { IFishingCourse } from './fishingCourse';
import { IUser } from './../pages/registration/registration/user';
import { IDateSpan } from './dateSpan';
import { ISortType } from './sortType';

export interface IFishingTrainer extends IUser {
  grade: number;
  biography: string;
  fishingCourse: IFishingCourse[];
  availableReservationDateSpan: IDateSpan[];
  unavailableReservationDateSpan: IDateSpan[];
}

export interface IFishingTrainerAvailability {
  name: string;
  dateSpan: IDateSpan;
  bedCapacity: number;
  price: number;
  grade: number;
  longitude: number;
  latitude: number;
  sortBy: ISortType[];
  freeAdditionalServices: string[];
}

export interface IFishingTrainerPage {
  content: IFishingTrainer[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}

export const emptyFishingTrainer: IFishingTrainer = {
  grade: 0,
  id: 0,
  firstName: '',
  lastName: '',
  username: '',
  password: '',
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
  biography: '',
  fishingCourse: [],
  availableReservationDateSpan: [],
  unavailableReservationDateSpan: [],
};

export const emptyFishingTrainerAvailability: IFishingTrainerAvailability = {
  name: '',
  dateSpan: {
    startDate: new Date(),
    endDate: new Date(),
  },
  bedCapacity: 0,
  price: 0,
  grade: -1,
  longitude: 0,
  latitude: 0,
  sortBy: [],
  freeAdditionalServices: [],
};
