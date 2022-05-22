import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateCustom'
})
export class DatePipe implements PipeTransform {

  transform(value: any): String {
    return value[2]+"/"+value[1]+"/"+value[0]+" "+value[3]+":"+value[4];
  }

}
