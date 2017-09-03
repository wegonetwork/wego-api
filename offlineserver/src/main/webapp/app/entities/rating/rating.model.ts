import { BaseEntity } from './../../shared';

export class Rating implements BaseEntity {
    constructor(
        public id?: number,
        public status?: boolean,
        public value?: number,
        public driver?: BaseEntity,
        public passenger?: BaseEntity,
    ) {
        this.status = false;
    }
}
