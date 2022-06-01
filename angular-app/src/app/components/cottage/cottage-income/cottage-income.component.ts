import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chart, registerables, ChartType, ChartItem } from 'chart.js';
import { IDateSpan } from 'src/app/model/dateSpan';
import { yearLabels, monthsLabels, initialData } from 'src/app/model/reservationCount';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { chartOpened } from 'src/app/model/income';


@Component({
  selector: 'app-cottage-income',
  templateUrl: './cottage-income.component.html',
  styleUrls: ['./cottage-income.component.css'],
})
export class CottageIncomeComponent implements OnInit {
  cottageId!: number;
  incomeChart!: Chart;
  currentTime: Date = new Date();
  incomeChartItem!: any;
  months: string[] = monthsLabels;
  yearLabels: string[] = yearLabels;
  monthsLabels!: string[];
  daysLabels: string[] = [];
  labels: string[] = [];
  duration: IDateSpan =
  {
    startDate:new Date(),
    endDate:new Date()
  };

  chartOpened: chartOpened = chartOpened.month;

  data: number[] = initialData;
  data1: number[] = initialData;
  data2: number[] = initialData;

  constructor(
    private _cottageService: CottageService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
    this.incomeChartItem = document.getElementById('incChart');
    Chart.register(...registerables);
    this.loadIncomeChart();
    this.yearLabels = this.addYearsLabels();
    this.monthsLabels = this.addMonthsLabels();
    this.daysLabels = this.addDaysLabels();
    this.incomeChart.data.labels = this.monthsLabels;
    this.incomeChart.update();
    this.incomeChartMonthly();
  }

  update() {
    this.incomeChartMonthly();
  }

  addYearsLabels():string[]{
    let labelsYear: string[] = [];
    let startYear = new Date(this.duration.startDate).getFullYear();
    let endYear = new Date(this.duration.endDate).getFullYear();
    for (startYear; startYear <= endYear; startYear++) {
      labelsYear.push(String(startYear));
    }
    return labelsYear;
  }

  
  addMonthsLabels(): string[] {  
    let labelsMonths: string[] = [];
    let startYear = new Date(this.duration.startDate).getFullYear();
    let endYear = new Date(this.duration.endDate).getFullYear();
    for (startYear; startYear <= endYear; startYear++) {
      for (let i=0; i <= 11; i++) {
        labelsMonths.push(this.months[i]+" "+String(startYear));
      }
    }
    return labelsMonths;
  }

  addDaysLabels(): string[] {
    let labelsDays: string[] = [];
    let startYear = new Date(this.duration.startDate).getFullYear();
    let endYear = new Date(this.duration.endDate).getFullYear();
    for (startYear; startYear <= endYear; startYear++) {
      for (let i=0; i <= 11; i++) {
         for (let j=1; j <= 31; j++) {
          labelsDays.push(String(j)+" "+this.months[i]+" "+String(startYear));
      }
      }
    }
    return labelsDays;
  }

  incomeChartYearly() {
    this.chartOpened = chartOpened.year;
    this.yearLabels = this.addYearsLabels();
    this.incomeChart.data.labels = this.yearLabels;
    this._cottageService
      .getCottageIncomeYearlyById(this.cottageId,this.duration)
      .subscribe((data) => {
        this.incomeChart.data.datasets[0].data = data.yearlySum;
        this.incomeChart.update();
      });
    this.incomeChart.config.type = 'pie' as ChartType;
    this.incomeChart.update();
  }

  incomeChartMonthly() {
    this.chartOpened = chartOpened.month;
    this.monthsLabels = this.addMonthsLabels();
    this.incomeChart.data.labels = this.monthsLabels;
    this.incomeChart.config.type = 'pie' as ChartType;
    this._cottageService
      .getCottageIncomeMonthlyById(this.cottageId,this.duration)
      .subscribe((data) => {
        let arr = [];
        for (let row of data.monthlySum) for (let e of row) arr.push(e);
        this.incomeChart.data.datasets[0].data = arr;
        this.incomeChart.update();
      });
    console.log(this.monthsLabels)
    this.incomeChart.update();
  }

  incomeChartDaily() {
    this.chartOpened = chartOpened.day;
    this.daysLabels = this.addDaysLabels();
    this.incomeChart.data.labels = this.daysLabels;
    this.incomeChart.config.type = 'pie' as ChartType;
    this._cottageService
      .getCottageIncomeDailyById(this.cottageId,this.duration)
      .subscribe((data) => {
        let arr = [];
        for (let table of data.dailySum) for (let row of table)  for (let e of row) arr.push(e);
        this.incomeChart.data.datasets[0].data = arr;
        this.incomeChart.update();
      });
    this.incomeChart.update();
  }


  dateChange():void{
    switch(this.chartOpened){
      case chartOpened.day:{
        this.yearLabels = this.addYearsLabels();
        this.incomeChartDaily();
        break;
      }
      case chartOpened.month:{
        this.monthsLabels = this.addMonthsLabels();
        this.incomeChartMonthly();
        break;
      }
      case chartOpened.year:{
        this.yearLabels = this.addYearsLabels();
        this.incomeChartYearly();
        break;
      }
    }
  }

  loadIncomeChart(): void {
    this.incomeChart = new Chart(this.incomeChartItem as ChartItem, {
      type: 'bar' as ChartType,
      data: {
        labels: this.labels,
        datasets: [
          {
            label: '#Income',
            data: this.data,
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(255, 159, 64, 0.2)',
              'rgba(155, 159, 64, 0.2)',
              'rgba(205, 205, 64, 0.2)',
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)',
              'rgba(155, 159, 64, 1)',
              'rgba(205, 205, 64, 1)',
            ],
            borderWidth: 1,
          },
        ],
      },
      options: {
        plugins: {
          datalabels: {
             display: function(context) {
                return context.dataset.data[context.dataIndex] == 0; // or >= 1 or ...
             }
          }
        },
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }
}
