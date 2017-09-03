import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PassengerComponent } from './passenger.component';
import { PassengerDetailComponent } from './passenger-detail.component';
import { PassengerPopupComponent } from './passenger-dialog.component';
import { PassengerDeletePopupComponent } from './passenger-delete-dialog.component';

@Injectable()
export class PassengerResolvePagingParams implements Resolve<any> {

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

export const passengerRoute: Routes = [
    {
        path: 'passenger',
        component: PassengerComponent,
        resolve: {
            'pagingParams': PassengerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passengers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'passenger/:id',
        component: PassengerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passengers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const passengerPopupRoute: Routes = [
    {
        path: 'passenger-new',
        component: PassengerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passengers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'passenger/:id/edit',
        component: PassengerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passengers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'passenger/:id/delete',
        component: PassengerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passengers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
