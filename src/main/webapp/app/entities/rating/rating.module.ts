import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    RatingService,
    RatingPopupService,
    RatingComponent,
    RatingDetailComponent,
    RatingDialogComponent,
    RatingPopupComponent,
    RatingDeletePopupComponent,
    RatingDeleteDialogComponent,
    ratingRoute,
    ratingPopupRoute,
    RatingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ratingRoute,
    ...ratingPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RatingComponent,
        RatingDetailComponent,
        RatingDialogComponent,
        RatingDeleteDialogComponent,
        RatingPopupComponent,
        RatingDeletePopupComponent,
    ],
    entryComponents: [
        RatingComponent,
        RatingDialogComponent,
        RatingPopupComponent,
        RatingDeleteDialogComponent,
        RatingDeletePopupComponent,
    ],
    providers: [
        RatingService,
        RatingPopupService,
        RatingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterRatingModule {}
