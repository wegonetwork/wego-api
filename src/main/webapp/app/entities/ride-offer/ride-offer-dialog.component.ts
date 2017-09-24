import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RideOffer } from './ride-offer.model';
import { RideOfferPopupService } from './ride-offer-popup.service';
import { RideOfferService } from './ride-offer.service';
import { Driver, DriverService } from '../driver';
import { Ride, RideService } from '../ride';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ride-offer-dialog',
    templateUrl: './ride-offer-dialog.component.html'
})
export class RideOfferDialogComponent implements OnInit {

    rideOffer: RideOffer;
    isSaving: boolean;

    drivers: Driver[];

    rides: Ride[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private rideOfferService: RideOfferService,
        private driverService: DriverService,
        private rideService: RideService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.driverService.query()
            .subscribe((res: ResponseWrapper) => { this.drivers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.rideService.query()
            .subscribe((res: ResponseWrapper) => { this.rides = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rideOffer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rideOfferService.update(this.rideOffer));
        } else {
            this.subscribeToSaveResponse(
                this.rideOfferService.create(this.rideOffer));
        }
    }

    private subscribeToSaveResponse(result: Observable<RideOffer>) {
        result.subscribe((res: RideOffer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RideOffer) {
        this.eventManager.broadcast({ name: 'rideOfferListModification', content: 'OK'});
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

    trackDriverById(index: number, item: Driver) {
        return item.id;
    }

    trackRideById(index: number, item: Ride) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ride-offer-popup',
    template: ''
})
export class RideOfferPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rideOfferPopupService: RideOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rideOfferPopupService
                    .open(RideOfferDialogComponent as Component, params['id']);
            } else {
                this.rideOfferPopupService
                    .open(RideOfferDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
