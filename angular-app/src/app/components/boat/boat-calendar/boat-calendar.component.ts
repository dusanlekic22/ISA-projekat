import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullCalendarComponent, EventApi, CalendarOptions, EventInput, DateSelectArg, EventClickArg } from '@fullcalendar/angular';
import { ModalDirective } from 'angular-bootstrap-md';
import { ToastrService } from 'ngx-toastr';
import { createEventId } from 'src/app/event-utils';
import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { emptyCustomer } from 'src/app/model/customer';
import { IDateSpan } from 'src/app/model/dateSpan';
import { BoatQuickReservationService } from 'src/app/pages/boat-owner/services/boat-quick-reservation.service';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';

@Component({
  selector: 'app-boat-calendar',
  templateUrl: './boat-calendar.component.html',
  styleUrls: ['./boat-calendar.component.css']
})
export class BoatCalendarComponent implements OnInit {
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

  activeReservations!: IBoatReservation[];
  passedReservations!: IBoatReservation[];
  quickReservations!: IBoatQuickReservation[];
  availableReservationDateSpans!: IDateSpan[];
  unavailableReservationDateSpans!: IDateSpan[];
  boat!: IBoat;
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
    guestCapacity: 0,
    price: 0,
    location: {
      street: '',
      city: '',
      country: '',
      latitude: 0,
      longitude: 0,
    },
    boat: initBoat,
    confirmed: false,
    customer: emptyCustomer,
  };

  constructor(
    private route: ActivatedRoute,
    private boatReservationService: BoatReservationService,
    private boatQuickReservationService: BoatQuickReservationService,
    private boatService: BoatService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadCalendar();
  }

  private loadCalendar() {
    let boatId = +this.route.snapshot.paramMap.get('boatId')!;
    this.boatService.getBoatById(boatId).subscribe((boat) => {
      if (boat != undefined) {
        const calendarApi = this.calendarComponent.getApi();
        calendarApi.removeAllEvents();
        this.boat = boat;
        this.availableReservationDateSpans =
          this.boat.availableReservationDateSpan;
        this.unavailableReservationDateSpans =
          this.boat.unavailableReservationDateSpan;
        this.availableReservationDateSpans.forEach((r) =>
          calendarApi.addEvent(this.createAvailableReservationEvent(r))
        );
        this.unavailableReservationDateSpans.forEach((r) =>
          calendarApi.addEvent(this.createUnavailableReservationEvent(r))
        );
        this.boatReservationService
          .getActiveBoatReservationByBoatId(this.boat.id)
          .subscribe((reservations) => {
            this.activeReservations = reservations;
            this.activeReservations.forEach((r) =>
              calendarApi.addEvent(this.createActiveReservationsEvent(r))
            );
          });
        this.boatReservationService
          .getPassedBoatReservationByBoatId(this.boat.id)
          .subscribe((reservations) => {
            this.passedReservations = reservations;
            this.passedReservations.forEach((r) =>
              calendarApi.addEvent(this.createPassedReservationsEvent(r))
            );
          });
        this.boatQuickReservationService
          .getBoatQuickReservationsByBoatId(this.boat.id)
          .subscribe((reservations) => {
            this.quickReservations = reservations;
            this.quickReservations.forEach((r) =>
              calendarApi.addEvent(this.createQuickReservationsEvent(r))
            );
          });
      }
    });
  }

  createActiveReservationsEvent(reservation: IBoatReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.boat.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#388cdc',
      reservation: reservation,
      type: 'activeReservation',
    };
  }

  createPassedReservationsEvent(reservation: IBoatReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.boat.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#639dd6',
      reservation: reservation,
      type: 'passedReservation',
    };
  }

  createQuickReservationsEvent(
    reservation: IBoatQuickReservation
  ): EventInput {
    return {
      id: createEventId(),
      title: `Action - ${reservation.boat.name}`,
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
    if (clickInfo.event.extendedProps.type === 'activeReservation') {
      this.reservation = clickInfo.event.extendedProps.reservation;
      this.showInfoModal();
    } else if (clickInfo.event.extendedProps.type === 'passedReservation') {
      this.reservation = clickInfo.event.extendedProps.reservation;
      this.showInfoModal();
    } else if (clickInfo.event.extendedProps.type === 'quickReservation') {
      this.reservation = clickInfo.event.extendedProps.reservation;
      this.showInfoModal();
    }
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents = events;
  }

  submit() {
    if (this.available) {
      this.boatService
        .editAvailableTerms(this.boat.id, this.availableDateSpan)
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
      this.boatService
        .editUnavailableTerms(this.boat.id, this.availableDateSpan)
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

  getCustomer(reservation: any) {
    return `${reservation.customer.firstName} ${reservation.customer.lastName}`;
  }
}
