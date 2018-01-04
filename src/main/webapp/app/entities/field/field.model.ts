import { BaseEntity } from './../../shared';

export const enum LayoutType {
    'DEFAULT',
    'STAR'
}

export class Field implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public origImageContentType?: string,
        public origImage?: any,
        public svgImageContentType?: string,
        public svgImage?: any,
        public layoutType?: LayoutType,
        public cards?: BaseEntity[],
    ) {
    }
}
