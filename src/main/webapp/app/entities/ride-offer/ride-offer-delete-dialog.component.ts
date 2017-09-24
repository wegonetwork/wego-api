import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RideOffer } from './ride-offer.model';
import { RideOfferPopupService } from './ride-offer-popup.service';
import { RideOfferService } from './ride-offer.service';

@Component({
    selector: 'jhi-ride-offer-delete-dialog',
    templateUrl: './ride-offer-delete-dialog.component.html'
})
export class RideOfferDeleteDialogComponent {

    rideOffer: RideOffer;

    constructor(
        private rideOfferService: RideOfferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rideOfferService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rideOfferListModification',
                content: 'Deleted an rideOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ride-offer-delete-popup',
    template: ''
})
export class RideOfferDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rideOfferPopupService: RideOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rideOfferPopupService
                .open(RideOfferDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
