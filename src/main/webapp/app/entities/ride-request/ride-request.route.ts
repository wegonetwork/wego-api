import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RideRequestComponent } from './ride-request.component';
import { RideRequestDetailComponent } from './ride-request-detail.component';
import { RideRequestPopupComponent } from './ride-request-dialog.component';
import { RideRequestDeletePopupComponent } from './ride-request-delete-dialog.component';

@Injectable()
export class RideRequestResolvePagingParams implements Resolve<any> {

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

export const rideRequestRoute: Routes = [
    {
        path: 'ride-request',
        component: RideRequestComponent,
        resolve: {
            'pagingParams': RideRequestResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideRequests'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ride-request/:id',
        component: RideRequestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideRequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rideRequestPopupRoute: Routes = [
    {
        path: 'ride-request-new',
        component: RideRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride-request/:id/edit',
        component: RideRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride-request/:id/delete',
        component: RideRequestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
