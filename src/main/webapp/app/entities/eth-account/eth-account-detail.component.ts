import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { EthAccount } from './eth-account.model';
import { EthAccountService } from './eth-account.service';

@Component({
    selector: 'jhi-eth-account-detail',
    templateUrl: './eth-account-detail.component.html'
})
export class EthAccountDetailComponent implements OnInit, OnDestroy {

    ethAccount: EthAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ethAccountService: EthAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEthAccounts();
    }

    load(id) {
        this.ethAccountService.find(id).subscribe((ethAccount) => {
            this.ethAccount = ethAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEthAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ethAccountListModification',
            (response) => this.load(this.ethAccount.id)
        );
    }
}
