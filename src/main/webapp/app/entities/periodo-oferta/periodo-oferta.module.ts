import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProcessoSeletivoSharedModule } from 'app/shared';
import {
    PeriodoOfertaComponent,
    PeriodoOfertaDetailComponent,
    PeriodoOfertaUpdateComponent,
    PeriodoOfertaDeletePopupComponent,
    PeriodoOfertaDeleteDialogComponent,
    periodoOfertaRoute,
    periodoOfertaPopupRoute
} from './';

const ENTITY_STATES = [...periodoOfertaRoute, ...periodoOfertaPopupRoute];

@NgModule({
    imports: [ProcessoSeletivoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PeriodoOfertaComponent,
        PeriodoOfertaDetailComponent,
        PeriodoOfertaUpdateComponent,
        PeriodoOfertaDeleteDialogComponent,
        PeriodoOfertaDeletePopupComponent
    ],
    entryComponents: [
        PeriodoOfertaComponent,
        PeriodoOfertaUpdateComponent,
        PeriodoOfertaDeleteDialogComponent,
        PeriodoOfertaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProcessoSeletivoPeriodoOfertaModule {}
