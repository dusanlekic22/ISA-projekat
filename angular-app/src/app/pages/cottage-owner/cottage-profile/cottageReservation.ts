import { IUser } from './../../registration/registration/user';
import { IDateSpan } from "./dateSpan";

export interface ICottageReservation{
    id:number;
    duration: IDateSpan;
    guestCapacity: number;
    price: number;
    customer: IUser;
}