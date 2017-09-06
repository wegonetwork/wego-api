import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rating } from './rating.model';
import { RatingPopupService } from './rating-popup.service';
import { RatingService } from './rating.service';

@Component({
    selector: 'jhi-rating-delete-dialog',
    templateUrl: './rating-delete-dialog.component.html'
})
export class RatingDeleteDialogComponent {

    rating: Rating;

    constructor(
        private ratingService: RatingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ratingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ratingListModification',
                content: 'Deleted an rating'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rating-delete-popup',
    template: ''
})
export class RatingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratingPopupService: RatingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ratingPopupService
                .open(RatingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
