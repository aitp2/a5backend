import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    WechatOrderService,
    WechatOrderPopupService,
    WechatOrderComponent,
    WechatOrderDetailComponent,
    WechatOrderDialogComponent,
    WechatOrderPopupComponent,
    WechatOrderDeletePopupComponent,
    WechatOrderDeleteDialogComponent,
    wechatOrderRoute,
    wechatOrderPopupRoute,
    WechatOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...wechatOrderRoute,
    ...wechatOrderPopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WechatOrderComponent,
        WechatOrderDetailComponent,
        WechatOrderDialogComponent,
        WechatOrderDeleteDialogComponent,
        WechatOrderPopupComponent,
        WechatOrderDeletePopupComponent,
    ],
    entryComponents: [
        WechatOrderComponent,
        WechatOrderDialogComponent,
        WechatOrderPopupComponent,
        WechatOrderDeleteDialogComponent,
        WechatOrderDeletePopupComponent,
    ],
    providers: [
        WechatOrderService,
        WechatOrderPopupService,
        WechatOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendWechatOrderModule {}
