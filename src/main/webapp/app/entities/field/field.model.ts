import { BaseEntity } from './../../shared';

export class Field implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public origImageContentType?: string,
        public origImage?: any,
        public svgImageContentType?: string,
        public svgImage?: any,
        public cards?: BaseEntity[],
    ) {
    }
}
