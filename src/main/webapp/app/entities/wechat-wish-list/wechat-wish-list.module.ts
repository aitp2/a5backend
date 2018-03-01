import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    WechatWishListService,
    WechatWishListPopupService,
    WechatWishListComponent,
    WechatWishListDetailComponent,
    WechatWishListDialogComponent,
    WechatWishListPopupComponent,
    WechatWishListDeletePopupComponent,
    WechatWishListDeleteDialogComponent,
    wechatWishListRoute,
    wechatWishListPopupRoute,
    WechatWishListResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...wechatWishListRoute,
    ...wechatWishListPopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WechatWishListComponent,
        WechatWishListDetailComponent,
        WechatWishListDialogComponent,
        WechatWishListDeleteDialogComponent,
        WechatWishListPopupComponent,
        WechatWishListDeletePopupComponent,
    ],
    entryComponents: [
        WechatWishListComponent,
        WechatWishListDialogComponent,
        WechatWishListPopupComponent,
        WechatWishListDeleteDialogComponent,
        WechatWishListDeletePopupComponent,
    ],
    providers: [
        WechatWishListService,
        WechatWishListPopupService,
        WechatWishListResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendWechatWishListModule {}
