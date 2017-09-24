import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rating } from './rating.model';
import { RatingPopupService } from './rating-popup.service';
import { RatingService } from './rating.service';
import { Driver, DriverService } from '../driver';
import { Passenger, PassengerService } from '../passenger';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rating-dialog',
    templateUrl: './rating-dialog.component.html'
})
export class RatingDialogComponent implements OnInit {

    rating: Rating;
    isSaving: boolean;

    drivers: Driver[];

    passengers: Passenger[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ratingService: RatingService,
        private driverService: DriverService,
        private passengerService: PassengerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.driverService.query()
            .subscribe((res: ResponseWrapper) => { this.drivers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.passengerService.query()
            .subscribe((res: ResponseWrapper) => { this.passengers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rating.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ratingService.update(this.rating));
        } else {
            this.subscribeToSaveResponse(
                this.ratingService.create(this.rating));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rating>) {
        result.subscribe((res: Rating) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Rating) {
        this.eventManager.broadcast({ name: 'ratingListModification', content: 'OK'});
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

    trackPassengerById(index: number, item: Passenger) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rating-popup',
    template: ''
})
export class RatingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratingPopupService: RatingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ratingPopupService
                    .open(RatingDialogComponent as Component, params['id']);
            } else {
                this.ratingPopupService
                    .open(RatingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
