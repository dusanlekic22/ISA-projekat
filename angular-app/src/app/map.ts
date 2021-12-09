export interface IGeometry {
  type: string;
  coordinates: number[];
}
export interface IGeoJSON {
  type: string;
  geometry: IGeometry;
  propeties?: any;
  $key?: string;
}

export class GeoJson implements IGeoJSON {
  type = 'Feature';
  geometry!: IGeometry;
  constructor(coordinates: number[], public properties: any) {
    this.geometry = {
      type: 'Point',
      coordinates: coordinates,
    };
  }
}

export class FeatureCollection {
  type = 'FeatureCollection';
  constructor(public features: Array<GeoJson>) {}
}
