import { BaseEntity } from './../../shared';

export class CollectList implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public collectProducts?: BaseEntity[],
    ) {
    }
}
