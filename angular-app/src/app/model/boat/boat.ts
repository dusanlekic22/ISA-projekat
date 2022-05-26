import { IUser } from "src/app/pages/registration/registration/user";
import { IAddress } from "../address";
import { IDateSpan } from "../dateSpan";
import { IImage } from "../image";
import { IBoatQuickReservation } from "./boatQuickReservation";
import { IBoatReservation } from "./boatReservation";


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
  bedCount: number;
  roomCount: number;
  boatRules: string;
  pricePerHour: number;
  cancelCondition: string;
  boatImage: IImage[];
  boatReservation: IBoatReservation[];
  boatQuickReservation: IBoatQuickReservation[];
  availableReservationDateSpan: IDateSpan[];
  boatOwner: IUser;
}
