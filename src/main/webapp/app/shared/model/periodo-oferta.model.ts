import { Moment } from 'moment';
import { IDemandaCurso } from 'app/shared/model//demanda-curso.model';

export interface IPeriodoOferta {
    id?: number;
    descricao?: string;
    dataInicio?: Moment;
    dataFim?: Moment;
    demandaCurso?: IDemandaCurso;
}

export class PeriodoOferta implements IPeriodoOferta {
    constructor(
        public id?: number,
        public descricao?: string,
        public dataInicio?: Moment,
        public dataFim?: Moment,
        public demandaCurso?: IDemandaCurso
    ) {}
}
