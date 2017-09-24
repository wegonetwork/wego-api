import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CountryComponent } from './country.component';
import { CountryDetailComponent } from './country-detail.component';
import { CountryPopupComponent } from './country-dialog.component';
import { CountryDeletePopupComponent } from './country-delete-dialog.component';

@Injectable()
export class CountryResolvePagingParams implements Resolve<any> {

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

export const countryRoute: Routes = [
    {
        path: 'country',
        component: CountryComponent,
        resolve: {
            'pagingParams': CountryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'country/:id',
        component: CountryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryPopupRoute: Routes = [
    {
        path: 'country-new',
        component: CountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country/:id/edit',
        component: CountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country/:id/delete',
        component: CountryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
