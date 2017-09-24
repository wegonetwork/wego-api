import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EthAccountComponent } from './eth-account.component';
import { EthAccountDetailComponent } from './eth-account-detail.component';
import { EthAccountPopupComponent } from './eth-account-dialog.component';
import { EthAccountDeletePopupComponent } from './eth-account-delete-dialog.component';

@Injectable()
export class EthAccountResolvePagingParams implements Resolve<any> {

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

export const ethAccountRoute: Routes = [
    {
        path: 'eth-account',
        component: EthAccountComponent,
        resolve: {
            'pagingParams': EthAccountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EthAccounts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'eth-account/:id',
        component: EthAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EthAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ethAccountPopupRoute: Routes = [
    {
        path: 'eth-account-new',
        component: EthAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EthAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eth-account/:id/edit',
        component: EthAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EthAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eth-account/:id/delete',
        component: EthAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EthAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
