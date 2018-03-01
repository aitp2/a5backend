import { BaseEntity } from './../../shared';

export class WechatWishList implements BaseEntity {
    constructor(
        public id?: number,
        public wishListProducts?: BaseEntity[],
        public wechatUserId?: number,
    ) {
    }
}
