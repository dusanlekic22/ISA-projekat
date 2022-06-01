export interface IIncome {
  yearlySum: number[];
  monthlySum: number[][];
  dailySum: number[][][];
}

export enum chartOpened {
  year,
  month,
  day,
}
