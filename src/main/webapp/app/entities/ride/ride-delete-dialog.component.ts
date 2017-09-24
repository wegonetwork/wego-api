import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ride } from './ride.model';
import { RidePopupService } from './ride-popup.service';
import { RideService } from './ride.service';

@Component({
    selector: 'jhi-ride-delete-dialog',
    templateUrl: './ride-delete-dialog.component.html'
})
export class RideDeleteDialogComponent {

    ride: Ride;

    constructor(
        private rideService: RideService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rideService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rideListModification',
                content: 'Deleted an ride'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ride-delete-popup',
    template: ''
})
export class RideDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ridePopupService: RidePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ridePopupService
                .open(RideDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
