import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EthAccount } from './eth-account.model';
import { EthAccountPopupService } from './eth-account-popup.service';
import { EthAccountService } from './eth-account.service';

@Component({
    selector: 'jhi-eth-account-delete-dialog',
    templateUrl: './eth-account-delete-dialog.component.html'
})
export class EthAccountDeleteDialogComponent {

    ethAccount: EthAccount;

    constructor(
        private ethAccountService: EthAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ethAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ethAccountListModification',
                content: 'Deleted an ethAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eth-account-delete-popup',
    template: ''
})
export class EthAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ethAccountPopupService: EthAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ethAccountPopupService
                .open(EthAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
