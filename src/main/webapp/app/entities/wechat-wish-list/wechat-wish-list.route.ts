import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatWishListComponent } from './wechat-wish-list.component';
import { WechatWishListDetailComponent } from './wechat-wish-list-detail.component';
import { WechatWishListPopupComponent } from './wechat-wish-list-dialog.component';
import { WechatWishListDeletePopupComponent } from './wechat-wish-list-delete-dialog.component';

@Injectable()
export class WechatWishListResolvePagingParams implements Resolve<any> {

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

export const wechatWishListRoute: Routes = [
    {
        path: 'wechat-wish-list',
        component: WechatWishListComponent,
        resolve: {
            'pagingParams': WechatWishListResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatWishLists'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-wish-list/:id',
        component: WechatWishListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatWishLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatWishListPopupRoute: Routes = [
    {
        path: 'wechat-wish-list-new',
        component: WechatWishListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatWishLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-wish-list/:id/edit',
        component: WechatWishListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatWishLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-wish-list/:id/delete',
        component: WechatWishListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatWishLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
