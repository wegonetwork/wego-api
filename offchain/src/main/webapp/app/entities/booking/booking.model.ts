import { BaseEntity } from './../../shared';

const enum BookingStatus {
    'CANCLED',
    'CONFIRMED',
    'REFUSED'
}

export class Booking implements BaseEntity {
    constructor(
        public id?: number,
        public status?: BookingStatus,
        public ride?: BaseEntity,
        public passenger?: BaseEntity,
    ) {
    }
}
