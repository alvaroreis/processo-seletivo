import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProcessoSeletivoSharedModule } from 'app/shared';
import {
    DemandaCursoComponent,
    DemandaCursoDetailComponent,
    DemandaCursoUpdateComponent,
    DemandaCursoDeletePopupComponent,
    DemandaCursoDeleteDialogComponent,
    demandaCursoRoute,
    demandaCursoPopupRoute
} from './';

const ENTITY_STATES = [...demandaCursoRoute, ...demandaCursoPopupRoute];

@NgModule({
    imports: [ProcessoSeletivoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DemandaCursoComponent,
        DemandaCursoDetailComponent,
        DemandaCursoUpdateComponent,
        DemandaCursoDeleteDialogComponent,
        DemandaCursoDeletePopupComponent
    ],
    entryComponents: [
        DemandaCursoComponent,
        DemandaCursoUpdateComponent,
        DemandaCursoDeleteDialogComponent,
        DemandaCursoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProcessoSeletivoDemandaCursoModule {}
