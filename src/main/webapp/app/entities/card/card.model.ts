import { BaseEntity } from './../../shared';

export const enum CardType {
    'TASK',
    'TRANSFER',
    'SUBJECT'
}

export class Card implements BaseEntity {
    constructor(
        public id?: number,
        public cardId?: number,
        public description?: string,
        public cardType?: CardType,
        public x1?: number,
        public y1?: number,
        public x2?: number,
        public y2?: number,
        public x3?: number,
        public y3?: number,
        public x4?: number,
        public y4?: number,
        public fieldId?: number,
        public tasks?: BaseEntity[],
        public subjectId?: number,
    ) {
    }
}
