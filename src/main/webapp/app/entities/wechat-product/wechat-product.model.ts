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
        public productQuantity?: number,
        public platformProduct?: boolean,
        public sellOut?: boolean,
        public goLive?: boolean,
        public collectTimes?: number,
        public wechatUserId?: number,
        public images?: BaseEntity[],
    ) {
        this.platformProduct = false;
        this.sellOut = false;
        this.goLive = false;
    }
}
