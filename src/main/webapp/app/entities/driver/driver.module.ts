import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WegoSharedModule } from '../../shared';
import { WegoAdminModule } from '../../admin/admin.module';
import {
    DriverService,
    DriverPopupService,
    DriverComponent,
    DriverDetailComponent,
    DriverDialogComponent,
    DriverPopupComponent,
    DriverDeletePopupComponent,
    DriverDeleteDialogComponent,
    driverRoute,
    driverPopupRoute,
    DriverResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...driverRoute,
    ...driverPopupRoute,
];

@NgModule({
    imports: [
        WegoSharedModule,
        WegoAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DriverComponent,
        DriverDetailComponent,
        DriverDialogComponent,
        DriverDeleteDialogComponent,
        DriverPopupComponent,
        DriverDeletePopupComponent,
    ],
    entryComponents: [
        DriverComponent,
        DriverDialogComponent,
        DriverPopupComponent,
        DriverDeleteDialogComponent,
        DriverDeletePopupComponent,
    ],
    providers: [
        DriverService,
        DriverPopupService,
        DriverResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoDriverModule {}
