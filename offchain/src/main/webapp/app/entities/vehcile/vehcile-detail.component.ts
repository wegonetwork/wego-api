import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Vehcile } from './vehcile.model';
import { VehcileService } from './vehcile.service';

@Component({
    selector: 'jhi-vehcile-detail',
    templateUrl: './vehcile-detail.component.html'
})
export class VehcileDetailComponent implements OnInit, OnDestroy {

    vehcile: Vehcile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vehcileService: VehcileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVehciles();
    }

    load(id) {
        this.vehcileService.find(id).subscribe((vehcile) => {
            this.vehcile = vehcile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVehciles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vehcileListModification',
            (response) => this.load(this.vehcile.id)
        );
    }
}
