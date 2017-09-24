import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    BookingService,
    BookingPopupService,
    BookingComponent,
    BookingDetailComponent,
    BookingDialogComponent,
    BookingPopupComponent,
    BookingDeletePopupComponent,
    BookingDeleteDialogComponent,
    bookingRoute,
    bookingPopupRoute,
    BookingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bookingRoute,
    ...bookingPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BookingComponent,
        BookingDetailComponent,
        BookingDialogComponent,
        BookingDeleteDialogComponent,
        BookingPopupComponent,
        BookingDeletePopupComponent,
    ],
    entryComponents: [
        BookingComponent,
        BookingDialogComponent,
        BookingPopupComponent,
        BookingDeleteDialogComponent,
        BookingDeletePopupComponent,
    ],
    providers: [
        BookingService,
        BookingPopupService,
        BookingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterBookingModule {}
