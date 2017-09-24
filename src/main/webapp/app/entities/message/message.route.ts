import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MessageComponent } from './message.component';
import { MessageDetailComponent } from './message-detail.component';
import { MessagePopupComponent } from './message-dialog.component';
import { MessageDeletePopupComponent } from './message-delete-dialog.component';

@Injectable()
export class MessageResolvePagingParams implements Resolve<any> {

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

export const messageRoute: Routes = [
    {
        path: 'message',
        component: MessageComponent,
        resolve: {
            'pagingParams': MessageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'message/:id',
        component: MessageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messagePopupRoute: Routes = [
    {
        path: 'message-new',
        component: MessagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message/:id/edit',
        component: MessagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message/:id/delete',
        component: MessageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
