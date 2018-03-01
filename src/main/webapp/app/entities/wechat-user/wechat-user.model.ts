import { BaseEntity } from './../../shared';

export class WechatUser implements BaseEntity {
    constructor(
        public id?: number,
        public openId?: string,
        public wechatCode?: string,
        public userName?: string,
        public mobileNum?: string,
        public project?: string,
        public seat?: string,
        public wechatWishListId?: number,
        public wechatProducts?: BaseEntity[],
    ) {
    }
}
