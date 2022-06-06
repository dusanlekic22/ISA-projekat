import { IUser } from "../pages/registration/registration/user";
import { IBoat } from "./boat/boat";
import { IBoatOwner } from "./boat/boatOwner";
import { ICottage } from "./cottage";
import { ICottageOwner } from "./cottageOwner";
import { emptyCustomer } from "./customer";
import { IFishingCourse } from "./fishingCourse";
import { IFishingTrainer } from "./fishingTrainer";
import { RequestStatus } from "./requestStatus";

export interface IGrade {
  id: number;
  value: number;
  user: IUser;
  review: string;
  isAccepted: RequestStatus;
  cottage: ICottage | null;
  cottageOwner: ICottageOwner | null;
  boat: IBoat | null;
  boatOwner: IBoatOwner | null;
  fishingCourse: IFishingCourse | null;
  fishingTrainer: IFishingTrainer | null;
}

export const emptyGrade: IGrade = {
  id: 0,
  isAccepted: RequestStatus.Waiting,
  review: '',
  value: 0,
  user: emptyCustomer,
  cottage: null,
  cottageOwner: null,
  boat: null,
  boatOwner: null,
  fishingCourse: null,
  fishingTrainer: null,
};
