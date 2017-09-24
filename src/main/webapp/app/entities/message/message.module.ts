import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import { JhipsterAdminModule } from '../../admin/admin.module';
import {
    MessageService,
    MessagePopupService,
    MessageComponent,
    MessageDetailComponent,
    MessageDialogComponent,
    MessagePopupComponent,
    MessageDeletePopupComponent,
    MessageDeleteDialogComponent,
    messageRoute,
    messagePopupRoute,
    MessageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...messageRoute,
    ...messagePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        JhipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MessageComponent,
        MessageDetailComponent,
        MessageDialogComponent,
        MessageDeleteDialogComponent,
        MessagePopupComponent,
        MessageDeletePopupComponent,
    ],
    entryComponents: [
        MessageComponent,
        MessageDialogComponent,
        MessagePopupComponent,
        MessageDeleteDialogComponent,
        MessageDeletePopupComponent,
    ],
    providers: [
        MessageService,
        MessagePopupService,
        MessageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterMessageModule {}
