import { ICurso } from 'app/shared/model//curso.model';

export interface IModalidade {
    id?: number;
    nome?: string;
    cursos?: ICurso[];
}

export class Modalidade implements IModalidade {
    constructor(public id?: number, public nome?: string, public cursos?: ICurso[]) {}
}
