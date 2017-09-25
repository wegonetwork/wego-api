import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LocationComponent } from './location.component';
import { LocationDetailComponent } from './location-detail.component';
import { LocationPopupComponent } from './location-dialog.component';
import { LocationDeletePopupComponent } from './location-delete-dialog.component';

@Injectable()
export class LocationResolvePagingParams implements Resolve<any> {

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

export const locationRoute: Routes = [
    {
        path: 'location',
        component: LocationComponent,
        resolve: {
            'pagingParams': LocationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'location/:id',
        component: LocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locationPopupRoute: Routes = [
    {
        path: 'location-new',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location/:id/edit',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location/:id/delete',
        component: LocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
