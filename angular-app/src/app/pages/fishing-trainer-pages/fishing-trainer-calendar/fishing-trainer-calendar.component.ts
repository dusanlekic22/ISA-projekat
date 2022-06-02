import { Component, OnInit, ViewChild } from '@angular/core';
import {
  FullCalendarComponent,
  EventApi,
  CalendarOptions,
  EventInput,
  DateSelectArg,
  EventClickArg,
} from '@fullcalendar/angular';
import { ModalDirective } from 'angular-bootstrap-md';
import { ToastrService } from 'ngx-toastr';
import { createEventId } from 'src/app/event-utils';
import { emptyCustomer } from 'src/app/model/customer';
import { IDateSpan } from 'src/app/model/dateSpan';
import { emptyFishingCourse } from 'src/app/model/fishingCourse';
import { IFishingQuickReservation } from 'src/app/model/fishingQuickReservation';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { IFishingTrainer } from 'src/app/model/fishingTrainer';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';
import { FishingTrainerService } from 'src/app/service/fishingTrainer.service';
import { ReservationReportService } from 'src/app/service/reservationReport.service';

@Component({
  selector: 'app-fishing-trainer-calendar',
  templateUrl: './fishing-trainer-calendar.component.html',
  styleUrls: ['./fishing-trainer-calendar.component.css'],
})
export class FishingTrainerCalendarComponent implements OnInit {
  @ViewChild('modal') addModal!: ModalDirective;
  @ViewChild('modalInfo') infoModal!: ModalDirective;
  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;
  currentEvents: EventApi[] = [];
  calendarOptions: CalendarOptions = {
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay',
    },
    initialView: 'dayGridMonth',
    events: [],
    weekends: true,
    editable: false,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this),
    selectOverlap: function (event) {
      return event.display === 'background';
    },
  };

  activeReservations!: IFishingReservation[];
  passedReservations!: IFishingReservation[];
  quickReservations!: IFishingQuickReservation[];
  availableReservationDateSpans!: IDateSpan[];
  unavailableReservationDateSpans!: IDateSpan[];
  fishingTrainer!: IFishingTrainer;
  available: boolean = true;
  availableDateSpan: IDateSpan = {
    startDate: new Date(),
    endDate: new Date(),
  };
  reservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    capacity: 0,
    price: 0,
    location: {
      street: '',
      city: '',
      country: '',
      latitude: 0,
      longitude: 0,
    },
    fishingCourse: emptyFishingCourse,
    confirmed: false,
    customer: emptyCustomer,
  };

  showReport: boolean = false;

  constructor(
    private fishingReservationService: FishingReservationService,
    private fishingQuickReservationService: FishingQuickReservationService,
    private fishingTrainerService: FishingTrainerService,
    private toastr: ToastrService,
    private reservationReportService: ReservationReportService
  ) {}

  ngOnInit(): void {
    this.loadCalendar();
  }

  private loadCalendar() {
    this.fishingTrainerService
      .getFishingTrainer()
      .subscribe((fishingTrainer) => {
        if (fishingTrainer != undefined) {
          const calendarApi = this.calendarComponent.getApi();
          calendarApi.removeAllEvents();
          this.fishingTrainer = fishingTrainer;
          this.availableReservationDateSpans =
            this.fishingTrainer.availableReservationDateSpan;
          this.unavailableReservationDateSpans =
            this.fishingTrainer.unavailableReservationDateSpan;
          this.availableReservationDateSpans.forEach((r) =>
            calendarApi.addEvent(this.createAvailableReservationEvent(r))
          );
          this.unavailableReservationDateSpans.forEach((r) =>
            calendarApi.addEvent(this.createUnavailableReservationEvent(r))
          );
          this.fishingReservationService
            .getActiveFishingReservationByFishingCourseByFishingTrainerId(
              fishingTrainer.id
            )
            .subscribe((reservations) => {
              this.activeReservations = reservations;
              this.activeReservations.forEach((r) =>
                calendarApi.addEvent(this.createActiveReservationsEvent(r))
              );
            });
          this.fishingReservationService
            .getPassedFishingReservationByFishingCourseByFishingTrainerId(
              fishingTrainer.id
            )
            .subscribe((reservations) => {
              this.passedReservations = reservations;
              this.passedReservations.forEach((r) =>
                calendarApi.addEvent(this.createPassedReservationsEvent(r))
              );
            });
          this.fishingQuickReservationService
            .getAllByFishingTrainerId(fishingTrainer.id)
            .subscribe((reservations) => {
              this.quickReservations = reservations;
              this.quickReservations.forEach((r) =>
                calendarApi.addEvent(this.createQuickReservationsEvent(r))
              );
            });
        }
      });
  }

  createActiveReservationsEvent(reservation: IFishingReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.fishingCourse.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#388cdc',
      reservation: reservation,
      type: 'activeReservation',
    };
  }

  createPassedReservationsEvent(reservation: IFishingReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.fishingCourse.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#639dd6',
      reservation: reservation,
      type: 'passedReservation',
    };
  }

  createQuickReservationsEvent(
    reservation: IFishingQuickReservation
  ): EventInput {
    return {
      id: createEventId(),
      title: `Action - ${reservation.fishingCourse.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#63bdd6',
      reservation: reservation,
      type: 'quickReservation',
    };
  }

  createAvailableReservationEvent(dateSpan: IDateSpan): EventInput {
    var difference =
      (new Date(dateSpan.endDate).getTime() -
        new Date(dateSpan.startDate).getTime()) /
      (1000 * 60 * 60 * 24.0);
    var allDay = Math.abs(difference) >= 1;
    return {
      id: createEventId(),
      title: 'Available time',
      start: dateSpan.startDate,
      end: dateSpan.endDate,
      display: 'background',
      backgroundColor: 'green',
      allDay: allDay,
    };
  }

  createUnavailableReservationEvent(dateSpan: IDateSpan): EventInput {
    var difference =
      (new Date(dateSpan.endDate).getTime() -
        new Date(dateSpan.startDate).getTime()) /
      (1000 * 60 * 60 * 24.0);
    var allDay = Math.abs(difference) >= 1;
    return {
      id: createEventId(),
      title: 'Unavailable time',
      start: dateSpan.startDate,
      end: dateSpan.endDate,
      display: 'background',
      backgroundColor: 'red',
      allDay: allDay,
    };
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    const calendarApi = this.calendarComponent.getApi();
    calendarApi.unselect(); // clear date selection
    this.availableDateSpan.startDate = new Date(
      Date.UTC(
        selectInfo.start.getFullYear(),
        selectInfo.start.getMonth(),
        selectInfo.start.getDate(),
        selectInfo.start.getHours(),
        selectInfo.start.getMinutes(),
        selectInfo.start.getSeconds()
      )
    );
    this.availableDateSpan.endDate = new Date(
      Date.UTC(
        selectInfo.end.getFullYear(),
        selectInfo.end.getMonth(),
        selectInfo.end.getDate(),
        selectInfo.end.getHours(),
        selectInfo.end.getMinutes(),
        selectInfo.start.getSeconds()
      )
    );
    this.showAddModal();
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.reservation = clickInfo.event.extendedProps.reservation;
    if (clickInfo.event.extendedProps.type === 'passedReservation') {
      this.reservationReportService
        .isReportedByFishingTrainer(this.reservation.id)
        .subscribe((reported) => {
          this.showReport = !reported;
        });
    } else {
      this.showReport = false;
    }
    this.showInfoModal();
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents = events;
  }

  submit() {
    if (this.available) {
      this.fishingTrainerService
        .editAvailableTerms(this.fishingTrainer.id, this.availableDateSpan)
        .subscribe(
          () => {
            this.loadCalendar();
            this.hideAddModal();
          },
          (err) => {
            this.toastr.error(
              'Theres already an active reservation in this date span.',
              'Try a different date!'
            );
          }
        );
    } else {
      this.fishingTrainerService
        .editUnavailableTerms(this.fishingTrainer.id, this.availableDateSpan)
        .subscribe(
          () => {
            this.loadCalendar();
            this.hideAddModal();
          },
          (err) => {
            this.toastr.error(
              'Theres already an active reservation in this date span.',
              'Try a different date!'
            );
          }
        );
    }
  }

  showAddModal() {
    this.addModal.show();
  }

  hideAddModal() {
    this.addModal.hide();
  }

  showInfoModal() {
    this.infoModal.show();
  }

  hideInfoModal() {
    this.infoModal.hide();
  }

  getLocation(reservation: any) {
    return `${reservation.location.country}, ${reservation.location.city}, ${reservation.location.street}`;
  }

  getCustomer(reservation: any) {
    return `${reservation.customer.firstName} ${reservation.customer.lastName}`;
  }
}
