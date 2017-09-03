import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RideRequest } from './ride-request.model';
import { RideRequestPopupService } from './ride-request-popup.service';
import { RideRequestService } from './ride-request.service';

@Component({
    selector: 'jhi-ride-request-delete-dialog',
    templateUrl: './ride-request-delete-dialog.component.html'
})
export class RideRequestDeleteDialogComponent {

    rideRequest: RideRequest;

    constructor(
        private rideRequestService: RideRequestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rideRequestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rideRequestListModification',
                content: 'Deleted an rideRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ride-request-delete-popup',
    template: ''
})
export class RideRequestDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rideRequestPopupService: RideRequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rideRequestPopupService
                .open(RideRequestDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
