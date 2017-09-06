import { BaseEntity, User } from './../../shared';

export class Vehcile implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public model?: string,
        public company?: string,
        public number?: number,
        public color?: string,
        public noSeats?: number,
        public carImageURL?: string,
        public user?: User,
    ) {
    }
}
