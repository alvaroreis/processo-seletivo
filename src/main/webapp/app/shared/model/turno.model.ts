import { ICurso } from 'app/shared/model//curso.model';

export interface ITurno {
    id?: number;
    nome?: string;
    cursos?: ICurso[];
}

export class Turno implements ITurno {
    constructor(public id?: number, public nome?: string, public cursos?: ICurso[]) {}
}
