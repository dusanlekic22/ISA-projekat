import { IFishingCourse } from 'src/app/model/fishingCourse';

export interface IFishingImage {
  id: number;
  image: string;
  fishingCourse: IFishingCourse;
}
