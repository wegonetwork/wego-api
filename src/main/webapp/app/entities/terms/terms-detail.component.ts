import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Terms } from './terms.model';
import { TermsService } from './terms.service';

@Component({
    selector: 'jhi-terms-detail',
    templateUrl: './terms-detail.component.html'
})
export class TermsDetailComponent implements OnInit, OnDestroy {

    terms: Terms;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private termsService: TermsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTerms();
    }

    load(id) {
        this.termsService.find(id).subscribe((terms) => {
            this.terms = terms;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTerms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'termsListModification',
            (response) => this.load(this.terms.id)
        );
    }
}
