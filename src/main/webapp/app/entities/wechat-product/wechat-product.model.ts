import { BaseEntity } from './../../shared';

export class WechatProduct implements BaseEntity {
    constructor(
        public id?: number,
        public productCode?: string,
        public productName?: string,
        public metaDesc?: string,
        public image?: string,
        public originalPrice?: number,
        public price?: number,
        public platformProduct?: boolean,
        public sellOut?: boolean,
        public online?: boolean,
        public wechatUserId?: number,
        public wechatWishListId?: number,
    ) {
        this.platformProduct = false;
        this.sellOut = false;
        this.online = false;
    }
}
