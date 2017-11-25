import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RideComponent } from './ride.component';
import { RideDetailComponent } from './ride-detail.component';
import { RidePopupComponent } from './ride-dialog.component';
import { RideDeletePopupComponent } from './ride-delete-dialog.component';

@Injectable()
export class RideResolvePagingParams implements Resolve<any> {

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

export const rideRoute: Routes = [
    {
        path: 'ride',
        component: RideComponent,
        resolve: {
            'pagingParams': RideResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rides'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ride/:id',
        component: RideDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rides'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ridePopupRoute: Routes = [
    {
        path: 'ride-new',
        component: RidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride/:id/edit',
        component: RidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride/:id/delete',
        component: RideDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rides'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
