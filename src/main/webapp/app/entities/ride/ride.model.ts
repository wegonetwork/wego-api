import { BaseEntity } from './../../shared';

const enum Gender {
    'MALE',
    'FEMALE'
}

const enum RideStatus {
    'CANCLED',
    'DELAYED',
    'EXPIRED'
}

export class Ride implements BaseEntity {
    constructor(
        public id?: number,
        public address?: string,
        public destination?: string,
        public mapPoint?: string,
        public rideDateTime?: any,
        public frequancy?: string,
        public seatGender?: Gender,
        public seatAge?: number,
        public smoking?: boolean,
        public price?: number,
        public luggage?: string,
        public mobileNumber?: number,
        public comment?: string,
        public status?: RideStatus,
        public vehcile?: BaseEntity,
        public city?: BaseEntity,
    ) {
        this.smoking = false;
    }
}
