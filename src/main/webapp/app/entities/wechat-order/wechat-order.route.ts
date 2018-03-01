import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatOrderComponent } from './wechat-order.component';
import { WechatOrderDetailComponent } from './wechat-order-detail.component';
import { WechatOrderPopupComponent } from './wechat-order-dialog.component';
import { WechatOrderDeletePopupComponent } from './wechat-order-delete-dialog.component';

@Injectable()
export class WechatOrderResolvePagingParams implements Resolve<any> {

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

export const wechatOrderRoute: Routes = [
    {
        path: 'wechat-order',
        component: WechatOrderComponent,
        resolve: {
            'pagingParams': WechatOrderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-order/:id',
        component: WechatOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatOrderPopupRoute: Routes = [
    {
        path: 'wechat-order-new',
        component: WechatOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-order/:id/edit',
        component: WechatOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-order/:id/delete',
        component: WechatOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
