import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Terms } from './terms.model';
import { TermsPopupService } from './terms-popup.service';
import { TermsService } from './terms.service';

@Component({
    selector: 'jhi-terms-dialog',
    templateUrl: './terms-dialog.component.html'
})
export class TermsDialogComponent implements OnInit {

    terms: Terms;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private termsService: TermsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.terms.id !== undefined) {
            this.subscribeToSaveResponse(
                this.termsService.update(this.terms));
        } else {
            this.subscribeToSaveResponse(
                this.termsService.create(this.terms));
        }
    }

    private subscribeToSaveResponse(result: Observable<Terms>) {
        result.subscribe((res: Terms) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Terms) {
        this.eventManager.broadcast({ name: 'termsListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-terms-popup',
    template: ''
})
export class TermsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private termsPopupService: TermsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.termsPopupService
                    .open(TermsDialogComponent as Component, params['id']);
            } else {
                this.termsPopupService
                    .open(TermsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
