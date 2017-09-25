import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public altitude?: string,
        public latitude?: string,
    ) {
    }
}
