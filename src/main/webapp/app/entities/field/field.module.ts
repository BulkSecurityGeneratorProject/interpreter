import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterpreterSharedModule } from '../../shared';
import {
    FieldService,
    FieldPopupService,
    FieldComponent,
    FieldDetailComponent,
    FieldDialogComponent,
    FieldPopupComponent,
    FieldDeletePopupComponent,
    FieldDeleteDialogComponent,
    fieldRoute,
    fieldPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fieldRoute,
    ...fieldPopupRoute,
];

@NgModule({
    imports: [
        InterpreterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FieldComponent,
        FieldDetailComponent,
        FieldDialogComponent,
        FieldDeleteDialogComponent,
        FieldPopupComponent,
        FieldDeletePopupComponent,
    ],
    entryComponents: [
        FieldComponent,
        FieldDialogComponent,
        FieldPopupComponent,
        FieldDeleteDialogComponent,
        FieldDeletePopupComponent,
    ],
    providers: [
        FieldService,
        FieldPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InterpreterFieldModule {}
