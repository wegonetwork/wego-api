import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Vehcile } from './vehcile.model';
import { VehcilePopupService } from './vehcile-popup.service';
import { VehcileService } from './vehcile.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vehcile-dialog',
    templateUrl: './vehcile-dialog.component.html'
})
export class VehcileDialogComponent implements OnInit {

    vehcile: Vehcile;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private vehcileService: VehcileService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehcile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehcileService.update(this.vehcile));
        } else {
            this.subscribeToSaveResponse(
                this.vehcileService.create(this.vehcile));
        }
    }

    private subscribeToSaveResponse(result: Observable<Vehcile>) {
        result.subscribe((res: Vehcile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Vehcile) {
        this.eventManager.broadcast({ name: 'vehcileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vehcile-popup',
    template: ''
})
export class VehcilePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehcilePopupService: VehcilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vehcilePopupService
                    .open(VehcileDialogComponent as Component, params['id']);
            } else {
                this.vehcilePopupService
                    .open(VehcileDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
