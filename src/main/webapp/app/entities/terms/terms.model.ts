import { BaseEntity } from './../../shared';

export class Terms implements BaseEntity {
    constructor(
        public id?: number,
        public terms?: string,
    ) {
    }
}
