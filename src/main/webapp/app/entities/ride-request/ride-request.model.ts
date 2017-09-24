import { BaseEntity } from './../../shared';

const enum RideStatus {
    'CANCLED',
    'DELAYED',
    'EXPIRED'
}

export class RideRequest implements BaseEntity {
    constructor(
        public id?: number,
        public status?: RideStatus,
        public ride?: BaseEntity,
        public passenger?: BaseEntity,
    ) {
    }
}
