import { IModalidade } from 'app/shared/model//modalidade.model';
import { ITurno } from 'app/shared/model//turno.model';
import { IUnidade } from 'app/shared/model//unidade.model';

export interface ICurso {
    id?: number;
    codSigaa?: number;
    nome?: string;
    cursoNovo?: number;
    justificativaNovo?: string;
    vagasAutorizadasMec?: number;
    resolucao?: string;
    modalidade?: IModalidade;
    turno?: ITurno;
    unidade?: IUnidade;
}

export class Curso implements ICurso {
    constructor(
        public id?: number,
        public codSigaa?: number,
        public nome?: string,
        public cursoNovo?: number,
        public justificativaNovo?: string,
        public vagasAutorizadasMec?: number,
        public resolucao?: string,
        public modalidade?: IModalidade,
        public turno?: ITurno,
        public unidade?: IUnidade
    ) {}
}
