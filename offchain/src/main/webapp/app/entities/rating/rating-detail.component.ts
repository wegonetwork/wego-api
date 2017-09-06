import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Rating } from './rating.model';
import { RatingService } from './rating.service';

@Component({
    selector: 'jhi-rating-detail',
    templateUrl: './rating-detail.component.html'
})
export class RatingDetailComponent implements OnInit, OnDestroy {

    rating: Rating;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ratingService: RatingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRatings();
    }

    load(id) {
        this.ratingService.find(id).subscribe((rating) => {
            this.rating = rating;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRatings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ratingListModification',
            (response) => this.load(this.rating.id)
        );
    }
}
