import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    WechatProductService,
    WechatProductPopupService,
    WechatProductComponent,
    WechatProductDetailComponent,
    WechatProductDialogComponent,
    WechatProductPopupComponent,
    WechatProductDeletePopupComponent,
    WechatProductDeleteDialogComponent,
    wechatProductRoute,
    wechatProductPopupRoute,
    WechatProductResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...wechatProductRoute,
    ...wechatProductPopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WechatProductComponent,
        WechatProductDetailComponent,
        WechatProductDialogComponent,
        WechatProductDeleteDialogComponent,
        WechatProductPopupComponent,
        WechatProductDeletePopupComponent,
    ],
    entryComponents: [
        WechatProductComponent,
        WechatProductDialogComponent,
        WechatProductPopupComponent,
        WechatProductDeleteDialogComponent,
        WechatProductDeletePopupComponent,
    ],
    providers: [
        WechatProductService,
        WechatProductPopupService,
        WechatProductResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendWechatProductModule {}
