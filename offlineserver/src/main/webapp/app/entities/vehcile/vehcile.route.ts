import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VehcileComponent } from './vehcile.component';
import { VehcileDetailComponent } from './vehcile-detail.component';
import { VehcilePopupComponent } from './vehcile-dialog.component';
import { VehcileDeletePopupComponent } from './vehcile-delete-dialog.component';

@Injectable()
export class VehcileResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const vehcileRoute: Routes = [
    {
        path: 'vehcile',
        component: VehcileComponent,
        resolve: {
            'pagingParams': VehcileResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehciles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vehcile/:id',
        component: VehcileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehciles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vehcilePopupRoute: Routes = [
    {
        path: 'vehcile-new',
        component: VehcilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehciles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehcile/:id/edit',
        component: VehcilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehciles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehcile/:id/delete',
        component: VehcileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehciles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
