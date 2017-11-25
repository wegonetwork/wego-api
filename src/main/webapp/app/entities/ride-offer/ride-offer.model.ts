import { BaseEntity } from './../../shared';

const enum RideStatus {
    'CANCLED',
    'DELAYED',
    'EXPIRED'
}

export class RideOffer implements BaseEntity {
    constructor(
        public id?: number,
        public status?: RideStatus,
        public driver?: BaseEntity,
        public ride?: BaseEntity,
    ) {
    }
}
