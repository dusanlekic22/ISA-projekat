import { IAddress } from "./address";
import { ICottageImage } from "./cottageImage";
import { ICottageQuickReservation } from "./cottageQuickReservation";
import { ICottageReservation } from "./cottageReservation";

export interface ICottage{
    id : number;
    name : string;
    address : IAddress;
    promoDescription : string;
    bedCount : number;
    roomCount : number;
    cottageRules : string;
    cottageImage : ICottageImage[];
    cottageReservation : ICottageReservation[];
    cottageQuickReservation :  ICottageQuickReservation[];
}