import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WegoSharedModule } from '../../shared';
import {
    RideRequestService,
    RideRequestPopupService,
    RideRequestComponent,
    RideRequestDetailComponent,
    RideRequestDialogComponent,
    RideRequestPopupComponent,
    RideRequestDeletePopupComponent,
    RideRequestDeleteDialogComponent,
    rideRequestRoute,
    rideRequestPopupRoute,
    RideRequestResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rideRequestRoute,
    ...rideRequestPopupRoute,
];

@NgModule({
    imports: [
        WegoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RideRequestComponent,
        RideRequestDetailComponent,
        RideRequestDialogComponent,
        RideRequestDeleteDialogComponent,
        RideRequestPopupComponent,
        RideRequestDeletePopupComponent,
    ],
    entryComponents: [
        RideRequestComponent,
        RideRequestDialogComponent,
        RideRequestPopupComponent,
        RideRequestDeleteDialogComponent,
        RideRequestDeletePopupComponent,
    ],
    providers: [
        RideRequestService,
        RideRequestPopupService,
        RideRequestResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoRideRequestModule {}
