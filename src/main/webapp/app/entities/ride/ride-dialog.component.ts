import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ride } from './ride.model';
import { RidePopupService } from './ride-popup.service';
import { RideService } from './ride.service';
import { Vehcile, VehcileService } from '../vehcile';
import { City, CityService } from '../city';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ride-dialog',
    templateUrl: './ride-dialog.component.html'
})
export class RideDialogComponent implements OnInit {

    ride: Ride;
    isSaving: boolean;

    vehciles: Vehcile[];

    cities: City[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private rideService: RideService,
        private vehcileService: VehcileService,
        private cityService: CityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.vehcileService.query()
            .subscribe((res: ResponseWrapper) => { this.vehciles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cityService.query()
            .subscribe((res: ResponseWrapper) => { this.cities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ride.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rideService.update(this.ride));
        } else {
            this.subscribeToSaveResponse(
                this.rideService.create(this.ride));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ride>) {
        result.subscribe((res: Ride) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Ride) {
        this.eventManager.broadcast({ name: 'rideListModification', content: 'OK'});
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

    trackVehcileById(index: number, item: Vehcile) {
        return item.id;
    }

    trackCityById(index: number, item: City) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ride-popup',
    template: ''
})
export class RidePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ridePopupService: RidePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ridePopupService
                    .open(RideDialogComponent as Component, params['id']);
            } else {
                this.ridePopupService
                    .open(RideDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
