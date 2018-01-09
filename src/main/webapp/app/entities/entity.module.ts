import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InterpreterFieldModule } from './field/field.module';
import { InterpreterCardModule } from './card/card.module';
import { InterpreterConnectionModule } from './connection/connection.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        InterpreterFieldModule,
        InterpreterCardModule,
        InterpreterConnectionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InterpreterEntityModule {}
