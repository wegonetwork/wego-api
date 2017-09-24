import { BaseEntity, User } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public message?: string,
        public subject?: string,
        public msgDateTime?: any,
        public userSender?: User,
        public userReciver?: User,
    ) {
    }
}
