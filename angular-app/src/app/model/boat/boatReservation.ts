import { ICustomer } from "../customer";
import { IDateSpan } from "../dateSpan";

export interface IBoatReservation{
    id:number;
    duration: IDateSpan;
    guestCapacity: number;
    price: number;
    customer: ICustomer;
    confirmed:boolean;
}