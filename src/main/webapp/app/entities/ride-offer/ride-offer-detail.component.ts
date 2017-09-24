import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RideOffer } from './ride-offer.model';
import { RideOfferService } from './ride-offer.service';

@Component({
    selector: 'jhi-ride-offer-detail',
    templateUrl: './ride-offer-detail.component.html'
})
export class RideOfferDetailComponent implements OnInit, OnDestroy {

    rideOffer: RideOffer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rideOfferService: RideOfferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRideOffers();
    }

    load(id) {
        this.rideOfferService.find(id).subscribe((rideOffer) => {
            this.rideOffer = rideOffer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRideOffers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rideOfferListModification',
            (response) => this.load(this.rideOffer.id)
        );
    }
}
