import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatOrderItemComponent } from './wechat-order-item.component';
import { WechatOrderItemDetailComponent } from './wechat-order-item-detail.component';
import { WechatOrderItemPopupComponent } from './wechat-order-item-dialog.component';
import { WechatOrderItemDeletePopupComponent } from './wechat-order-item-delete-dialog.component';

@Injectable()
export class WechatOrderItemResolvePagingParams implements Resolve<any> {

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

export const wechatOrderItemRoute: Routes = [
    {
        path: 'wechat-order-item',
        component: WechatOrderItemComponent,
        resolve: {
            'pagingParams': WechatOrderItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrderItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-order-item/:id',
        component: WechatOrderItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrderItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatOrderItemPopupRoute: Routes = [
    {
        path: 'wechat-order-item-new',
        component: WechatOrderItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-order-item/:id/edit',
        component: WechatOrderItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-order-item/:id/delete',
        component: WechatOrderItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
