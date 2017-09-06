import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WegoSharedModule } from '../../shared';
import {
    RideService,
    RidePopupService,
    RideComponent,
    RideDetailComponent,
    RideDialogComponent,
    RidePopupComponent,
    RideDeletePopupComponent,
    RideDeleteDialogComponent,
    rideRoute,
    ridePopupRoute,
    RideResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rideRoute,
    ...ridePopupRoute,
];

@NgModule({
    imports: [
        WegoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RideComponent,
        RideDetailComponent,
        RideDialogComponent,
        RideDeleteDialogComponent,
        RidePopupComponent,
        RideDeletePopupComponent,
    ],
    entryComponents: [
        RideComponent,
        RideDialogComponent,
        RidePopupComponent,
        RideDeleteDialogComponent,
        RideDeletePopupComponent,
    ],
    providers: [
        RideService,
        RidePopupService,
        RideResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoRideModule {}
