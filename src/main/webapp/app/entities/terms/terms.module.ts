import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WegoSharedModule } from '../../shared';
import {
    TermsService,
    TermsPopupService,
    TermsComponent,
    TermsDetailComponent,
    TermsDialogComponent,
    TermsPopupComponent,
    TermsDeletePopupComponent,
    TermsDeleteDialogComponent,
    termsRoute,
    termsPopupRoute,
    TermsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...termsRoute,
    ...termsPopupRoute,
];

@NgModule({
    imports: [
        WegoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TermsComponent,
        TermsDetailComponent,
        TermsDialogComponent,
        TermsDeleteDialogComponent,
        TermsPopupComponent,
        TermsDeletePopupComponent,
    ],
    entryComponents: [
        TermsComponent,
        TermsDialogComponent,
        TermsPopupComponent,
        TermsDeleteDialogComponent,
        TermsDeletePopupComponent,
    ],
    providers: [
        TermsService,
        TermsPopupService,
        TermsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoTermsModule {}
