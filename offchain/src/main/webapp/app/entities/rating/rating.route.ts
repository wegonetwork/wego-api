import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RatingComponent } from './rating.component';
import { RatingDetailComponent } from './rating-detail.component';
import { RatingPopupComponent } from './rating-dialog.component';
import { RatingDeletePopupComponent } from './rating-delete-dialog.component';

@Injectable()
export class RatingResolvePagingParams implements Resolve<any> {

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

export const ratingRoute: Routes = [
    {
        path: 'rating',
        component: RatingComponent,
        resolve: {
            'pagingParams': RatingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ratings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rating/:id',
        component: RatingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ratings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ratingPopupRoute: Routes = [
    {
        path: 'rating-new',
        component: RatingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ratings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rating/:id/edit',
        component: RatingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ratings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rating/:id/delete',
        component: RatingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ratings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
