import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Ride } from './ride.model';
import { RideService } from './ride.service';

@Component({
    selector: 'jhi-ride-detail',
    templateUrl: './ride-detail.component.html'
})
export class RideDetailComponent implements OnInit, OnDestroy {

    ride: Ride;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rideService: RideService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRides();
    }

    load(id) {
        this.rideService.find(id).subscribe((ride) => {
            this.ride = ride;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRides() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rideListModification',
            (response) => this.load(this.ride.id)
        );
    }
}
