import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Passenger } from './passenger.model';
import { PassengerService } from './passenger.service';

@Component({
    selector: 'jhi-passenger-detail',
    templateUrl: './passenger-detail.component.html'
})
export class PassengerDetailComponent implements OnInit, OnDestroy {

    passenger: Passenger;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private passengerService: PassengerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPassengers();
    }

    load(id) {
        this.passengerService.find(id).subscribe((passenger) => {
            this.passenger = passenger;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPassengers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'passengerListModification',
            (response) => this.load(this.passenger.id)
        );
    }
}
