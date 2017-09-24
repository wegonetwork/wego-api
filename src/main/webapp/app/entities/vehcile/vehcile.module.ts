import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import { JhipsterAdminModule } from '../../admin/admin.module';
import {
    VehcileService,
    VehcilePopupService,
    VehcileComponent,
    VehcileDetailComponent,
    VehcileDialogComponent,
    VehcilePopupComponent,
    VehcileDeletePopupComponent,
    VehcileDeleteDialogComponent,
    vehcileRoute,
    vehcilePopupRoute,
    VehcileResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...vehcileRoute,
    ...vehcilePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        JhipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VehcileComponent,
        VehcileDetailComponent,
        VehcileDialogComponent,
        VehcileDeleteDialogComponent,
        VehcilePopupComponent,
        VehcileDeletePopupComponent,
    ],
    entryComponents: [
        VehcileComponent,
        VehcileDialogComponent,
        VehcilePopupComponent,
        VehcileDeleteDialogComponent,
        VehcileDeletePopupComponent,
    ],
    providers: [
        VehcileService,
        VehcilePopupService,
        VehcileResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterVehcileModule {}
