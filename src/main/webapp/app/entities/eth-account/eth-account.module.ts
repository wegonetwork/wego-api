import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    EthAccountService,
    EthAccountPopupService,
    EthAccountComponent,
    EthAccountDetailComponent,
    EthAccountDialogComponent,
    EthAccountPopupComponent,
    EthAccountDeletePopupComponent,
    EthAccountDeleteDialogComponent,
    ethAccountRoute,
    ethAccountPopupRoute,
    EthAccountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ethAccountRoute,
    ...ethAccountPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EthAccountComponent,
        EthAccountDetailComponent,
        EthAccountDialogComponent,
        EthAccountDeleteDialogComponent,
        EthAccountPopupComponent,
        EthAccountDeletePopupComponent,
    ],
    entryComponents: [
        EthAccountComponent,
        EthAccountDialogComponent,
        EthAccountPopupComponent,
        EthAccountDeleteDialogComponent,
        EthAccountDeletePopupComponent,
    ],
    providers: [
        EthAccountService,
        EthAccountPopupService,
        EthAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEthAccountModule {}
