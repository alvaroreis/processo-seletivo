import { IUsuario } from 'app/shared/model//usuario.model';
import { ICurso } from 'app/shared/model//curso.model';
import { IDemandaCurso } from 'app/shared/model//demanda-curso.model';
import { ICidade } from 'app/shared/model//cidade.model';

export interface IUnidade {
    id?: number;
    nome?: string;
    telefone?: string;
    abreviacao?: string;
    usuario?: IUsuario;
    cursos?: ICurso[];
    demandaCursos?: IDemandaCurso[];
    cidade?: ICidade;
}

export class Unidade implements IUnidade {
    constructor(
        public id?: number,
        public nome?: string,
        public telefone?: string,
        public abreviacao?: string,
        public usuario?: IUsuario,
        public cursos?: ICurso[],
        public demandaCursos?: IDemandaCurso[],
        public cidade?: ICidade
    ) {}
}
