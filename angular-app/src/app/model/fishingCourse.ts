import { IFishingTrainer } from './fishingTrainer';
import { IFishingReservation } from './fishingReservation';
import { IFishingQuickReservation } from './fishingQuickReservation';
import { IFishingImage } from './fishingImage';
import { IAdditionalService } from './additionalService';
import { IAddress } from './address';

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
  fishingImage: [],
  capacity: 0,
  fishingReservation: [],
  fishingQuickReservation: [],
  fishingRules: '',
  fishingEquipment: '',
  cancellationPercentageKeep: 0,
  additionalService: [],
  price: 0,
  fishingTrainer: {
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
    biography: '',
    fishingCourse: [],
    availableReservationDateSpan: [],
    unavailableReservationDateSpan: [],
  },
};
