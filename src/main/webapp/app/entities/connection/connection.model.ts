import { BaseEntity } from './../../shared';

export class Connection implements BaseEntity {
    constructor(
        public id?: number,
        public uuid?: number,
        public name?: string,
        public endPoint1Uuid?: number,
        public endPoint1X?: number,
        public endPoint1Y?: number,
        public endPoint1Angle?: number,
        public directed1?: boolean,
        public endPoint2Uuid?: number,
        public endPoint2X?: number,
        public endPoint2Y?: number,
        public endPoint2Angle?: number,
        public directed2?: boolean,
        public fieldId?: number,
    ) {
        this.directed1 = false;
        this.directed2 = false;
    }
}
