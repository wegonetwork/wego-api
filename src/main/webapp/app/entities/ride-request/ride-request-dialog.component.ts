import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RideRequest } from './ride-request.model';
import { RideRequestPopupService } from './ride-request-popup.service';
import { RideRequestService } from './ride-request.service';
import { Ride, RideService } from '../ride';
import { Passenger, PassengerService } from '../passenger';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ride-request-dialog',
    templateUrl: './ride-request-dialog.component.html'
})
export class RideRequestDialogComponent implements OnInit {

    rideRequest: RideRequest;
    isSaving: boolean;

    rides: Ride[];

    passengers: Passenger[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private rideRequestService: RideRequestService,
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
        if (this.rideRequest.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rideRequestService.update(this.rideRequest));
        } else {
            this.subscribeToSaveResponse(
                this.rideRequestService.create(this.rideRequest));
        }
    }

    private subscribeToSaveResponse(result: Observable<RideRequest>) {
        result.subscribe((res: RideRequest) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RideRequest) {
        this.eventManager.broadcast({ name: 'rideRequestListModification', content: 'OK'});
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
    selector: 'jhi-ride-request-popup',
    template: ''
})
export class RideRequestPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rideRequestPopupService: RideRequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rideRequestPopupService
                    .open(RideRequestDialogComponent as Component, params['id']);
            } else {
                this.rideRequestPopupService
                    .open(RideRequestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
