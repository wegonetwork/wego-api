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
        public mapPoint?: string,
        public frequancy?: string,
        public destination?: string,
        public seatAge?: number,
        public mobileNumber?: number,
        public seatGender?: Gender,
        public rideDateTime?: any,
        public smoking?: boolean,
        public price?: number,
        public comment?: string,
        public status?: RideStatus,
        public vehcile?: BaseEntity,
        public city?: BaseEntity,
    ) {
        this.smoking = false;
    }
}
