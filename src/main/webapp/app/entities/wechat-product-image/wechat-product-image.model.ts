import { BaseEntity } from './../../shared';

export class WechatProductImage implements BaseEntity {
    constructor(
        public id?: number,
        public imageUrl?: string,
        public imageDesc?: string,
        public imageContent?: any,
        public wechatProductId?: number,
    ) {
    }
}
