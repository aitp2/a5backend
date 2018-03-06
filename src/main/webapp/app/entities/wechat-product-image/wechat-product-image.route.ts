import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatProductImageComponent } from './wechat-product-image.component';
import { WechatProductImageDetailComponent } from './wechat-product-image-detail.component';
import { WechatProductImagePopupComponent } from './wechat-product-image-dialog.component';
import { WechatProductImageDeletePopupComponent } from './wechat-product-image-delete-dialog.component';

@Injectable()
export class WechatProductImageResolvePagingParams implements Resolve<any> {

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

export const wechatProductImageRoute: Routes = [
    {
        path: 'wechat-product-image',
        component: WechatProductImageComponent,
        resolve: {
            'pagingParams': WechatProductImageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProductImages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-product-image/:id',
        component: WechatProductImageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProductImages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatProductImagePopupRoute: Routes = [
    {
        path: 'wechat-product-image-new',
        component: WechatProductImagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProductImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-product-image/:id/edit',
        component: WechatProductImagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProductImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-product-image/:id/delete',
        component: WechatProductImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatProductImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
