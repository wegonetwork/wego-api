import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EthAccount } from './eth-account.model';
import { EthAccountPopupService } from './eth-account-popup.service';
import { EthAccountService } from './eth-account.service';

@Component({
    selector: 'jhi-eth-account-dialog',
    templateUrl: './eth-account-dialog.component.html'
})
export class EthAccountDialogComponent implements OnInit {

    ethAccount: EthAccount;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ethAccountService: EthAccountService,
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
        if (this.ethAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ethAccountService.update(this.ethAccount));
        } else {
            this.subscribeToSaveResponse(
                this.ethAccountService.create(this.ethAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<EthAccount>) {
        result.subscribe((res: EthAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EthAccount) {
        this.eventManager.broadcast({ name: 'ethAccountListModification', content: 'OK'});
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
    selector: 'jhi-eth-account-popup',
    template: ''
})
export class EthAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ethAccountPopupService: EthAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ethAccountPopupService
                    .open(EthAccountDialogComponent as Component, params['id']);
            } else {
                this.ethAccountPopupService
                    .open(EthAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
