import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Passenger } from './passenger.model';
import { PassengerPopupService } from './passenger-popup.service';
import { PassengerService } from './passenger.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-passenger-dialog',
    templateUrl: './passenger-dialog.component.html'
})
export class PassengerDialogComponent implements OnInit {

    passenger: Passenger;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private passengerService: PassengerService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.passenger.id !== undefined) {
            this.subscribeToSaveResponse(
                this.passengerService.update(this.passenger));
        } else {
            this.subscribeToSaveResponse(
                this.passengerService.create(this.passenger));
        }
    }

    private subscribeToSaveResponse(result: Observable<Passenger>) {
        result.subscribe((res: Passenger) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Passenger) {
        this.eventManager.broadcast({ name: 'passengerListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-passenger-popup',
    template: ''
})
export class PassengerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private passengerPopupService: PassengerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.passengerPopupService
                    .open(PassengerDialogComponent as Component, params['id']);
            } else {
                this.passengerPopupService
                    .open(PassengerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
