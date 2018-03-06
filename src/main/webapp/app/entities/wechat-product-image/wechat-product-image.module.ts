import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    WechatProductImageService,
    WechatProductImagePopupService,
    WechatProductImageComponent,
    WechatProductImageDetailComponent,
    WechatProductImageDialogComponent,
    WechatProductImagePopupComponent,
    WechatProductImageDeletePopupComponent,
    WechatProductImageDeleteDialogComponent,
    wechatProductImageRoute,
    wechatProductImagePopupRoute,
    WechatProductImageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...wechatProductImageRoute,
    ...wechatProductImagePopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WechatProductImageComponent,
        WechatProductImageDetailComponent,
        WechatProductImageDialogComponent,
        WechatProductImageDeleteDialogComponent,
        WechatProductImagePopupComponent,
        WechatProductImageDeletePopupComponent,
    ],
    entryComponents: [
        WechatProductImageComponent,
        WechatProductImageDialogComponent,
        WechatProductImagePopupComponent,
        WechatProductImageDeleteDialogComponent,
        WechatProductImageDeletePopupComponent,
    ],
    providers: [
        WechatProductImageService,
        WechatProductImagePopupService,
        WechatProductImageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendWechatProductImageModule {}
