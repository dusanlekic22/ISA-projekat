import { ActivatedRoute } from '@angular/router';
import { monthsLabels, initialData } from './../../../model/reservationCount';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { Chart, ChartItem, ChartType, registerables } from 'chart.js';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-boat-statistics',
  templateUrl: './boat-statistics.component.html',
  styleUrls: ['./boat-statistics.component.css'],
})
export class BoatStatisticsComponent implements OnInit {
  boatId!: number;
  reservationChart!: Chart;
  currentTime: Date = new Date();
  reservationChartItem!: any;
  yearLabels: string[] = [
    (this.currentTime.getFullYear() - 2).toString(),
    (this.currentTime.getFullYear() - 1).toString(),
    this.currentTime.getFullYear().toString(),
    (this.currentTime.getFullYear() + 1).toString(),
  ];
  monthsLabels: string[] = monthsLabels;
  weeksLabels: string[] = [];
  labels: string[] = [];

  monthlySum: number[] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
  weeklySum: number[] = [0, 0, 0, 0];
  daysAppSum: number[] = [0, 0, 0, 0, 0, 0, 0];

  data: number[] = initialData;
  data1: number[] = initialData;
  data2: number[] = initialData;

  constructor(private _boatService: BoatService,
    private _route:ActivatedRoute) {}

  ngOnInit(): void {
    this.boatId = +this._route.snapshot.paramMap.get('boatId')!;
    this.reservationChartItem = document.getElementById('resChart');
    Chart.register(...registerables);
    this.loadReservationChart();
    this.weeksLabels = this.addWeeksLabels();
    this.reservationChart.data.labels = this.monthsLabels;
    this.reservationChart.update();
    this.reservationChartMonthly();
  }

  update(){  
    this.reservationChartMonthly();
  }

  addWeeksLabels(): string[] {
    let labelsWeeks: string[] = [];
    var month = this.currentTime.getMonth();
    var d = new Date(this.currentTime.getFullYear(), month, 0);
    var lastDay = d.getDate();
    for (let i = 1; i <= 3; i++) {
      labelsWeeks.push(String(i * 7));
      console.log(i);
    }
    labelsWeeks.push(String(lastDay));
    return labelsWeeks;
  }

  reservationChartYearly() {
    this.reservationChart.data.labels = this.yearLabels;
    this._boatService
      .getBoatReservationYearlyById(this.boatId)
      .subscribe((data) => {
        this.reservationChart.data.datasets[0].data = data.yearlySum;
        this.reservationChart.update();
      });
    this.reservationChart.config.type = 'pie' as ChartType;
    this.reservationChart.update();
  }

  reservationChartMonthly() {
    this.reservationChart.data.labels = this.monthsLabels;
    this.reservationChart.config.type = 'pie' as ChartType;
    this._boatService
      .getBoatReservationMonthlyById(this.boatId)
      .subscribe((data) => {
        this.reservationChart.data.datasets[0].data = data.monthlySum;
        this.reservationChart.update();
      });
    this.reservationChart.update();
  }

  reservationChartWeekly() {
    this.reservationChart.data.labels = this.weeksLabels;
    this.reservationChart.config.type = 'pie' as ChartType;
    this._boatService
      .getBoatReservationWeeklyById(this.boatId)
      .subscribe((data) => {
        this.reservationChart.data.datasets[0].data = data.weeklySum;
        this.reservationChart.update();
      });
    this.reservationChart.update();
  }

  loadReservationChart(): void {
    this.reservationChart = new Chart(this.reservationChartItem as ChartItem, {
      type: 'bar' as ChartType,
      data: {
        labels: this.labels,
        datasets: [
          {
            label: '#Number of appointments',
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
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }
}
