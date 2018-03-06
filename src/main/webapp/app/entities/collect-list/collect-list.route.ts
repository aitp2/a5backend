import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CollectListComponent } from './collect-list.component';
import { CollectListDetailComponent } from './collect-list-detail.component';
import { CollectListPopupComponent } from './collect-list-dialog.component';
import { CollectListDeletePopupComponent } from './collect-list-delete-dialog.component';

@Injectable()
export class CollectListResolvePagingParams implements Resolve<any> {

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

export const collectListRoute: Routes = [
    {
        path: 'collect-list',
        component: CollectListComponent,
        resolve: {
            'pagingParams': CollectListResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CollectLists'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collect-list/:id',
        component: CollectListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CollectLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collectListPopupRoute: Routes = [
    {
        path: 'collect-list-new',
        component: CollectListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CollectLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collect-list/:id/edit',
        component: CollectListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CollectLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collect-list/:id/delete',
        component: CollectListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CollectLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
