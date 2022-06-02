import { ICottageOwner } from './../../../model/cottageOwner';
import { CottageOwnerService } from './../../../pages/cottage-owner/services/cottage-owner.service';
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
import { initCottage } from 'src/app/model/cottage';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { emptyCustomer } from 'src/app/model/customer';
import { IDateSpan } from 'src/app/model/dateSpan';
import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ReservationReportService } from 'src/app/service/reservationReport.service';

@Component({
  selector: 'app-cottage-owner-calendar',
  templateUrl: './cottage-owner-calendar.component.html',
  styleUrls: ['./cottage-owner-calendar.component.css'],
})
export class CottageOwnerCalendarComponent implements OnInit {
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

  activeReservations!: ICottageReservation[];
  passedReservations!: ICottageReservation[];
  quickReservations!: ICottageQuickReservation[];
  unavailableReservationDateSpans!: IDateSpan[];
  cottageOwner!: ICottageOwner;
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
    cottage: initCottage,
    confirmed: false,
    customer: emptyCustomer,
  };

  showReport: boolean = false;

  constructor(
    private cottageReservationService: CottageReservationService,
    private cottageQuickReservationService: CottageQuickReservationService,
    private cottageOwnerService: CottageOwnerService,
    private toastr: ToastrService,
    private reservationReportService: ReservationReportService
  ) {}

  ngOnInit(): void {
    this.loadCalendar();
  }

  private loadCalendar() {
    this.cottageOwnerService.getCottageOwner().subscribe((cottageOwner) => {
      if (cottageOwner != undefined) {
        const calendarApi = this.calendarComponent.getApi();
        calendarApi.removeAllEvents();
        this.cottageOwner = cottageOwner;
        this.unavailableReservationDateSpans =
          this.cottageOwner.unavailableReservationDateSpan;
        this.unavailableReservationDateSpans.forEach((r) =>
          calendarApi.addEvent(this.createUnavailableReservationEvent(r))
        );
        this.cottageReservationService
          .getActiveCottageReservationByCottageByCottageOwnerId(
            this.cottageOwner.id
          )
          .subscribe((reservations) => {
            this.activeReservations = reservations;
            this.activeReservations.forEach((r) =>
              calendarApi.addEvent(this.createActiveReservationsEvent(r))
            );
          });
        this.cottageReservationService
          .getPassedCottageReservationByCottageByCottageOwnerId(
            this.cottageOwner.id
          )
          .subscribe((reservations) => {
            this.passedReservations = reservations;
            this.passedReservations.forEach((r) =>
              calendarApi.addEvent(this.createPassedReservationsEvent(r))
            );
          });
        this.cottageQuickReservationService
          .getAllByCottageOwnerId(
            this.cottageOwner.id
          )
          .subscribe((reservations) => {
            this.quickReservations = reservations;
            this.quickReservations.forEach((r) =>
              calendarApi.addEvent(this.createQuickReservationsEvent(r))
            );
          });
      }
    });
  }

  createActiveReservationsEvent(reservation: ICottageReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.cottage.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#388cdc',
      reservation: reservation,
      type: 'activeReservation',
    };
  }

  createPassedReservationsEvent(reservation: ICottageReservation): EventInput {
    return {
      id: createEventId(),
      title: `Reservation - ${reservation.cottage.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#639dd6',
      reservation: reservation,
      type: 'passedReservation',
    };
  }

  createQuickReservationsEvent(
    reservation: ICottageQuickReservation
  ): EventInput {
    return {
      id: createEventId(),
      title: `Action - ${reservation.cottage.name}`,
      start: reservation.duration.startDate,
      end: reservation.duration.endDate,
      color: '#63bdd6',
      reservation: reservation,
      type: 'quickReservation',
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
        .isReportedByCottageOwner(this.reservation.id)
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
    this.cottageOwnerService
      .editUnavailableTerms(this.cottageOwner.id, this.availableDateSpan)
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
