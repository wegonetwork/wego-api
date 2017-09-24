import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Passenger } from './passenger.model';
import { PassengerPopupService } from './passenger-popup.service';
import { PassengerService } from './passenger.service';

@Component({
    selector: 'jhi-passenger-delete-dialog',
    templateUrl: './passenger-delete-dialog.component.html'
})
export class PassengerDeleteDialogComponent {

    passenger: Passenger;

    constructor(
        private passengerService: PassengerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.passengerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'passengerListModification',
                content: 'Deleted an passenger'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-passenger-delete-popup',
    template: ''
})
export class PassengerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private passengerPopupService: PassengerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.passengerPopupService
                .open(PassengerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
