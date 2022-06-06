import { IFishingCourse } from './fishingCourse';
import { IUser } from './../pages/registration/registration/user';
import { IDateSpan } from './dateSpan';
import { ISortType } from './sortType';
import { ILoyaltyProgram, LoyaltyRank } from './loyaltyProgram';
import { IGrade } from './grade';

export interface IFishingTrainer extends IUser {
  grade: number;
  biography: string;
  fishingCourse: IFishingCourse[];
  availableReservationDateSpan: IDateSpan[];
  unavailableReservationDateSpan: IDateSpan[];
  averageGrade: number;
  loyaltyProgram: ILoyaltyProgram;
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
  grades: IGrade[];
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
  averageGrade: 0,
  fishingCourse: [],
  availableReservationDateSpan: [],
  unavailableReservationDateSpan: [],
  loyaltyProgram: {
    loyaltyRank: LoyaltyRank.Regular,
    points: 0,
  },
  deleted: false,
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
  grades: [],
};
