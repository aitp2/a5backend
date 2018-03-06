import { BaseEntity } from './../../shared';

export class WechatOrderItem implements BaseEntity {
    constructor(
        public id?: number,
        public price?: number,
        public quantity?: number,
        public retailPrice?: number,
        public productId?: number,
        public wechatOrderId?: number,
    ) {
    }
}
