import { IFishingTrainer, emptyFishingTrainer } from './fishingTrainer';
import { IFishingReservation } from './fishingReservation';
import { IFishingQuickReservation } from './fishingQuickReservation';
import { IFishingImage } from './fishingImage';
import { IAdditionalService } from './additionalService';
import { IAddress } from './address';
import { IDateSpan } from './dateSpan';
import { ISortType } from './sortType';
import { IGrade } from './grade';
import { ICustomer } from './customer';

export interface IFishingCourse {
  id: number;
  name: string;
  address: IAddress;
  promoDescription: string;
  fishingImage: IFishingImage[];
  capacity: number;
  fishingQuickReservation: IFishingQuickReservation[];
  fishingRules: string;
  fishingEquipment: string;
  additionalService: IAdditionalService[];
  fishingReservation: IFishingReservation[];
  price: number;
  cancellationPercentageKeep: number;
  fishingTrainer: IFishingTrainer;
  averageGrade: number;
  subscribers: ICustomer[];
}
export interface IFishingCoursePage {
  content: IFishingCourse[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
}

export interface IFishingCourseAvailability {
  name: string;
  dateSpan: IDateSpan;
  bedCapacity: number;
  price: number;
  grade: number;
  longitude: number;
  latitude: number;
  fishingTrainerId: number;
  sortBy: ISortType[];
  freeAdditionalServices: string[];
}

export const emptyFishingCourse: IFishingCourse = {
  id: 0,
  name: '',
  address: {
    city: '',
    country: '',
    latitude: 0,
    longitude: 0,
    street: '',
  },
  promoDescription: '',
  averageGrade: 0,
  fishingImage: [],
  capacity: 0,
  fishingReservation: [],
  fishingQuickReservation: [],
  fishingRules: '',
  fishingEquipment: '',
  cancellationPercentageKeep: 0,
  additionalService: [],
  subscribers: [],
  price: 0,
  fishingTrainer: emptyFishingTrainer,
};

export const emptyFishingCourseAvailability: IFishingCourseAvailability = {
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
  fishingTrainerId: 0,
  sortBy: [],
  freeAdditionalServices: [],
};
