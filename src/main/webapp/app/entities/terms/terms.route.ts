import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TermsComponent } from './terms.component';
import { TermsDetailComponent } from './terms-detail.component';
import { TermsPopupComponent } from './terms-dialog.component';
import { TermsDeletePopupComponent } from './terms-delete-dialog.component';

@Injectable()
export class TermsResolvePagingParams implements Resolve<any> {

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

export const termsRoute: Routes = [
    {
        path: 'terms',
        component: TermsComponent,
        resolve: {
            'pagingParams': TermsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Terms'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'terms/:id',
        component: TermsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Terms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const termsPopupRoute: Routes = [
    {
        path: 'terms-new',
        component: TermsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Terms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'terms/:id/edit',
        component: TermsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Terms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'terms/:id/delete',
        component: TermsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Terms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
