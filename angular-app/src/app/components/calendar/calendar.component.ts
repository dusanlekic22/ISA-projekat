import { IFishingQuickReservation } from 'src/app/model/fishingQuickReservation';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { IDateSpan } from 'src/app/model/dateSpan';
import { IFishingTrainer } from './../../model/fishingTrainer';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { FishingTrainerService } from 'src/app/service/fishingTrainer.service';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  CalendarOptions,
  EventApi,
  DateSelectArg,
  EventClickArg,
  EventInput,
  FullCalendarComponent,
} from '@fullcalendar/angular';
import { createEventId } from 'src/app/event-utils';
import { ModalDirective } from 'angular-bootstrap-md';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
})
export class CalendarComponent implements OnInit {
  @ViewChild('modal') addModal!: ModalDirective;
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
  fishingTrainer!: IFishingTrainer;
  available: boolean = true;
  availableDateSpan: IDateSpan = {
    startDate: new Date(),
    endDate: new Date(),
  };

  constructor(
    private fishingReservationService: FishingReservationService,
    private fishingQuickReservationService: FishingQuickReservationService,
    private fishingTrainerService: FishingTrainerService,
    private toastr: ToastrService
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
          this.availableReservationDateSpans.forEach((r) =>
            calendarApi.addEvent(this.createAvailableReservationEvent(r))
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
    };
  }

  createPassedReservationsEvent(reservation: IFishingReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.fishingCourse.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#639dd6',
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
    };
  }

  createAvailableReservationEvent(dateSpan: IDateSpan): EventInput {
    return {
      id: createEventId(),
      title: 'Available time',
      start: dateSpan.startDate,
      end: dateSpan.endDate,
      display: 'background',
      backgroundColor: 'green',
      allDay: true,
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
    if (
      confirm(
        `Are you sure you want to delete the event '${clickInfo.event.title}'`
      )
    ) {
      clickInfo.event.remove();
    }
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
          },
          (err) => {
            this.toastr.error(
              'Theres already an active reservation in this date span.',
              'Try a different date!'
            );
          }
        );
    } else {
    }
    this.hideAddModal();
  }

  showAddModal() {
    this.addModal.show();
  }

  hideAddModal() {
    this.addModal.hide();
  }
}
