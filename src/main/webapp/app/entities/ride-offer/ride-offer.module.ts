import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    RideOfferService,
    RideOfferPopupService,
    RideOfferComponent,
    RideOfferDetailComponent,
    RideOfferDialogComponent,
    RideOfferPopupComponent,
    RideOfferDeletePopupComponent,
    RideOfferDeleteDialogComponent,
    rideOfferRoute,
    rideOfferPopupRoute,
    RideOfferResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rideOfferRoute,
    ...rideOfferPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RideOfferComponent,
        RideOfferDetailComponent,
        RideOfferDialogComponent,
        RideOfferDeleteDialogComponent,
        RideOfferPopupComponent,
        RideOfferDeletePopupComponent,
    ],
    entryComponents: [
        RideOfferComponent,
        RideOfferDialogComponent,
        RideOfferPopupComponent,
        RideOfferDeleteDialogComponent,
        RideOfferDeletePopupComponent,
    ],
    providers: [
        RideOfferService,
        RideOfferPopupService,
        RideOfferResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterRideOfferModule {}
