import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Terms } from './terms.model';
import { TermsPopupService } from './terms-popup.service';
import { TermsService } from './terms.service';

@Component({
    selector: 'jhi-terms-delete-dialog',
    templateUrl: './terms-delete-dialog.component.html'
})
export class TermsDeleteDialogComponent {

    terms: Terms;

    constructor(
        private termsService: TermsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.termsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'termsListModification',
                content: 'Deleted an terms'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-terms-delete-popup',
    template: ''
})
export class TermsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private termsPopupService: TermsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.termsPopupService
                .open(TermsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
