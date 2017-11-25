import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Booking } from './booking.model';
import { BookingPopupService } from './booking-popup.service';
import { BookingService } from './booking.service';

@Component({
    selector: 'jhi-booking-delete-dialog',
    templateUrl: './booking-delete-dialog.component.html'
})
export class BookingDeleteDialogComponent {

    booking: Booking;

    constructor(
        private bookingService: BookingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bookingListModification',
                content: 'Deleted an booking'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-booking-delete-popup',
    template: ''
})
export class BookingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookingPopupService: BookingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bookingPopupService
                .open(BookingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
