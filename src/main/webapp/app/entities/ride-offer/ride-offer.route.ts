import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RideOfferComponent } from './ride-offer.component';
import { RideOfferDetailComponent } from './ride-offer-detail.component';
import { RideOfferPopupComponent } from './ride-offer-dialog.component';
import { RideOfferDeletePopupComponent } from './ride-offer-delete-dialog.component';

@Injectable()
export class RideOfferResolvePagingParams implements Resolve<any> {

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

export const rideOfferRoute: Routes = [
    {
        path: 'ride-offer',
        component: RideOfferComponent,
        resolve: {
            'pagingParams': RideOfferResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideOffers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ride-offer/:id',
        component: RideOfferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideOffers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rideOfferPopupRoute: Routes = [
    {
        path: 'ride-offer-new',
        component: RideOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride-offer/:id/edit',
        component: RideOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ride-offer/:id/delete',
        component: RideOfferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RideOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
