import { IBoat } from 'src/app/model/boat/boat';
import { IDateSpan } from "../dateSpan";

export interface IBoatQuickReservation{
    id:number;
    duration : IDateSpan;
    guestCapacity: number;
    price: number;
    boat: IBoat;
}
