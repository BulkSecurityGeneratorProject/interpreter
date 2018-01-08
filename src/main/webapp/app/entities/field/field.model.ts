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
        public resultImageContentType?: string,
        public resultImage?: any,
        public xmlDataContentType?: string,
        public xmlData?: any,
        public cards?: BaseEntity[],
        public connections?: BaseEntity[],
    ) {
    }
}
