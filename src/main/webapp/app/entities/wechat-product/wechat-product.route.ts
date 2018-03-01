import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatProductComponent } from './wechat-product.component';
import { WechatProductDetailComponent } from './wechat-product-detail.component';
import { WechatProductPopupComponent } from './wechat-product-dialog.component';
import { WechatProductDeletePopupComponent } from './wechat-product-delete-dialog.component';

@Injectable()
export class WechatProductResolvePagingParams implements Resolve<any> {

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

export const wechatProductRoute: Routes = [
    {
        path: 'wechat-product',
        component: WechatProductComponent,
        resolve: {
            'pagingParams': WechatProductResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProducts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-product/:id',
        component: WechatProductDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatProductPopupRoute: Routes = [
    {
        path: 'wechat-product-new',
        component: WechatProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-product/:id/edit',
        component: WechatProductPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-product/:id/delete',
        component: WechatProductDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
