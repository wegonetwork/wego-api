import { BaseEntity } from './../../shared';

export class EthAccount implements BaseEntity {
    constructor(
        public id?: number,
        public ethaccount?: string,
    ) {
    }
}
