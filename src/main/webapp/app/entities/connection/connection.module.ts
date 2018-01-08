import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterpreterSharedModule } from '../../shared';
import {
    ConnectionService,
    ConnectionPopupService,
    ConnectionComponent,
    ConnectionDetailComponent,
    ConnectionDialogComponent,
    ConnectionPopupComponent,
    ConnectionDeletePopupComponent,
    ConnectionDeleteDialogComponent,
    connectionRoute,
    connectionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...connectionRoute,
    ...connectionPopupRoute,
];

@NgModule({
    imports: [
        InterpreterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConnectionComponent,
        ConnectionDetailComponent,
        ConnectionDialogComponent,
        ConnectionDeleteDialogComponent,
        ConnectionPopupComponent,
        ConnectionDeletePopupComponent,
    ],
    entryComponents: [
        ConnectionComponent,
        ConnectionDialogComponent,
        ConnectionPopupComponent,
        ConnectionDeleteDialogComponent,
        ConnectionDeletePopupComponent,
    ],
    providers: [
        ConnectionService,
        ConnectionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InterpreterConnectionModule {}
