import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public relateTo?: number,
        public userName?: string,
        public userId?: number,
        public icon?: string,
        public feedbacks?: BaseEntity[],
        public questionId?: number,
    ) {
    }
}
