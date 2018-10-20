import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ProcessoSeletivoModalidadeModule } from './modalidade/modalidade.module';
import { ProcessoSeletivoCursoModule } from './curso/curso.module';
import { ProcessoSeletivoTurnoModule } from './turno/turno.module';
import { ProcessoSeletivoDemandaCursoModule } from './demanda-curso/demanda-curso.module';
import { ProcessoSeletivoPeriodoOfertaModule } from './periodo-oferta/periodo-oferta.module';
import { ProcessoSeletivoUsuarioModule } from './usuario/usuario.module';
import { ProcessoSeletivoPerfilModule } from './perfil/perfil.module';
import { ProcessoSeletivoUnidadeModule } from './unidade/unidade.module';
import { ProcessoSeletivoCidadeModule } from './cidade/cidade.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ProcessoSeletivoModalidadeModule,
        ProcessoSeletivoCursoModule,
        ProcessoSeletivoTurnoModule,
        ProcessoSeletivoDemandaCursoModule,
        ProcessoSeletivoPeriodoOfertaModule,
        ProcessoSeletivoUsuarioModule,
        ProcessoSeletivoPerfilModule,
        ProcessoSeletivoUnidadeModule,
        ProcessoSeletivoCidadeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProcessoSeletivoEntityModule {}
