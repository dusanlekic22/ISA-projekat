import { IAdditionalService } from './../pages/cottage-owner/cottage-profile/additionalService';
import { IAddress } from './../pages/registration/registration/user';
import { IFishingTrainer } from './fishingTrainer';
import { IFishingReservation } from './fishingReservation';
import { IFishingQuickReservation } from './fishingQuickReservation';
import { IFishingImage } from './fishingImage';

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
