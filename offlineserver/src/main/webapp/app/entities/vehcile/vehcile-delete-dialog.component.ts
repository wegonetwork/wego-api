import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Vehcile } from './vehcile.model';
import { VehcilePopupService } from './vehcile-popup.service';
import { VehcileService } from './vehcile.service';

@Component({
    selector: 'jhi-vehcile-delete-dialog',
    templateUrl: './vehcile-delete-dialog.component.html'
})
export class VehcileDeleteDialogComponent {

    vehcile: Vehcile;

    constructor(
        private vehcileService: VehcileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vehcileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vehcileListModification',
                content: 'Deleted an vehcile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vehcile-delete-popup',
    template: ''
})
export class VehcileDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehcilePopupService: VehcilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vehcilePopupService
                .open(VehcileDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
