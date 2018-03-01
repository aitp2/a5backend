import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { A5BackendWechatUserModule } from './wechat-user/wechat-user.module';
import { A5BackendWechatOrderModule } from './wechat-order/wechat-order.module';
import { A5BackendWechatProductModule } from './wechat-product/wechat-product.module';
import { A5BackendWechatWishListModule } from './wechat-wish-list/wechat-wish-list.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        A5BackendWechatUserModule,
        A5BackendWechatOrderModule,
        A5BackendWechatProductModule,
        A5BackendWechatWishListModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendEntityModule {}
