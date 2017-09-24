import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Booking } from './booking.model';
import { BookingPopupService } from './booking-popup.service';
import { BookingService } from './booking.service';
import { Ride, RideService } from '../ride';
import { Passenger, PassengerService } from '../passenger';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-booking-dialog',
    templateUrl: './booking-dialog.component.html'
})
export class BookingDialogComponent implements OnInit {

    booking: Booking;
    isSaving: boolean;

    rides: Ride[];

    passengers: Passenger[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bookingService: BookingService,
        private rideService: RideService,
        private passengerService: PassengerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.rideService.query()
            .subscribe((res: ResponseWrapper) => { this.rides = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.passengerService.query()
            .subscribe((res: ResponseWrapper) => { this.passengers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.booking.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bookingService.update(this.booking));
        } else {
            this.subscribeToSaveResponse(
                this.bookingService.create(this.booking));
        }
    }

    private subscribeToSaveResponse(result: Observable<Booking>) {
        result.subscribe((res: Booking) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Booking) {
        this.eventManager.broadcast({ name: 'bookingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackRideById(index: number, item: Ride) {
        return item.id;
    }

    trackPassengerById(index: number, item: Passenger) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-booking-popup',
    template: ''
})
export class BookingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookingPopupService: BookingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bookingPopupService
                    .open(BookingDialogComponent as Component, params['id']);
            } else {
                this.bookingPopupService
                    .open(BookingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
