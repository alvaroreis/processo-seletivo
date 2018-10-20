import { Moment } from 'moment';
import { IPeriodoOferta } from 'app/shared/model//periodo-oferta.model';
import { IUnidade } from 'app/shared/model//unidade.model';

export interface IDemandaCurso {
    id?: number;
    data?: Moment;
    resolucao?: string;
    periodoOfertas?: IPeriodoOferta[];
    unidade?: IUnidade;
}

export class DemandaCurso implements IDemandaCurso {
    constructor(
        public id?: number,
        public data?: Moment,
        public resolucao?: string,
        public periodoOfertas?: IPeriodoOferta[],
        public unidade?: IUnidade
    ) {}
}
