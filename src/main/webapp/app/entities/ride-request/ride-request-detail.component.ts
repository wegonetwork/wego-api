import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RideRequest } from './ride-request.model';
import { RideRequestService } from './ride-request.service';

@Component({
    selector: 'jhi-ride-request-detail',
    templateUrl: './ride-request-detail.component.html'
})
export class RideRequestDetailComponent implements OnInit, OnDestroy {

    rideRequest: RideRequest;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rideRequestService: RideRequestService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRideRequests();
    }

    load(id) {
        this.rideRequestService.find(id).subscribe((rideRequest) => {
            this.rideRequest = rideRequest;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRideRequests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rideRequestListModification',
            (response) => this.load(this.rideRequest.id)
        );
    }
}
