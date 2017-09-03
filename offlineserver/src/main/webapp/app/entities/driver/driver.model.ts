import { BaseEntity, User } from './../../shared';

export class Driver implements BaseEntity {
    constructor(
        public id?: number,
        public fullName?: string,
        public user?: User,
    ) {
    }
}
