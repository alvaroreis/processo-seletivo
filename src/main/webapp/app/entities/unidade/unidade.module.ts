import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProcessoSeletivoSharedModule } from 'app/shared';
import {
    UnidadeComponent,
    UnidadeDetailComponent,
    UnidadeUpdateComponent,
    UnidadeDeletePopupComponent,
    UnidadeDeleteDialogComponent,
    unidadeRoute,
    unidadePopupRoute
} from './';

const ENTITY_STATES = [...unidadeRoute, ...unidadePopupRoute];

@NgModule({
    imports: [ProcessoSeletivoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnidadeComponent,
        UnidadeDetailComponent,
        UnidadeUpdateComponent,
        UnidadeDeleteDialogComponent,
        UnidadeDeletePopupComponent
    ],
    entryComponents: [UnidadeComponent, UnidadeUpdateComponent, UnidadeDeleteDialogComponent, UnidadeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProcessoSeletivoUnidadeModule {}
