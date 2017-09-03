import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WegoSharedModule } from '../../shared';
import { WegoAdminModule } from '../../admin/admin.module';
import {
    PassengerService,
    PassengerPopupService,
    PassengerComponent,
    PassengerDetailComponent,
    PassengerDialogComponent,
    PassengerPopupComponent,
    PassengerDeletePopupComponent,
    PassengerDeleteDialogComponent,
    passengerRoute,
    passengerPopupRoute,
    PassengerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...passengerRoute,
    ...passengerPopupRoute,
];

@NgModule({
    imports: [
        WegoSharedModule,
        WegoAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PassengerComponent,
        PassengerDetailComponent,
        PassengerDialogComponent,
        PassengerDeleteDialogComponent,
        PassengerPopupComponent,
        PassengerDeletePopupComponent,
    ],
    entryComponents: [
        PassengerComponent,
        PassengerDialogComponent,
        PassengerPopupComponent,
        PassengerDeleteDialogComponent,
        PassengerDeletePopupComponent,
    ],
    providers: [
        PassengerService,
        PassengerPopupService,
        PassengerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoPassengerModule {}
