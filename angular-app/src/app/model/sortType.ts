export interface ISortType {
  text: string;
  field: string;
  direction: string;
}

export const sortTypes: ISortType[] = [
  {
    text: 'Price ascending',
    field: 'price',
    direction: 'asc',
  },
  {
    text: 'Price descending',
    field: 'price',
    direction: 'desc',
  },
  {
    text: 'Grade ascending',
    field: 'grade',
    direction: 'asc',
  },
  {
    text: 'Grade descending',
    field: 'grade',
    direction: 'desc',
  },
  {
    text: 'Name ascending',
    field: 'name',
    direction: 'asc',
  },
  {
    text: 'Name descending',
    field: 'name',
    direction: 'desc',
  },
  {
    text: 'Location descending',
    field: 'latitude',
    direction: 'desc',
  },
  {
    text: 'Location asscending',
    field: 'latitude',
    direction: 'asc',
  },
];
