import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    WechatOrderItemService,
    WechatOrderItemPopupService,
    WechatOrderItemComponent,
    WechatOrderItemDetailComponent,
    WechatOrderItemDialogComponent,
    WechatOrderItemPopupComponent,
    WechatOrderItemDeletePopupComponent,
    WechatOrderItemDeleteDialogComponent,
    wechatOrderItemRoute,
    wechatOrderItemPopupRoute,
    WechatOrderItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...wechatOrderItemRoute,
    ...wechatOrderItemPopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WechatOrderItemComponent,
        WechatOrderItemDetailComponent,
        WechatOrderItemDialogComponent,
        WechatOrderItemDeleteDialogComponent,
        WechatOrderItemPopupComponent,
        WechatOrderItemDeletePopupComponent,
    ],
    entryComponents: [
        WechatOrderItemComponent,
        WechatOrderItemDialogComponent,
        WechatOrderItemPopupComponent,
        WechatOrderItemDeleteDialogComponent,
        WechatOrderItemDeletePopupComponent,
    ],
    providers: [
        WechatOrderItemService,
        WechatOrderItemPopupService,
        WechatOrderItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendWechatOrderItemModule {}
