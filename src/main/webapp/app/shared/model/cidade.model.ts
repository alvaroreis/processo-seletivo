import { IUnidade } from 'app/shared/model//unidade.model';

export interface ICidade {
    id?: number;
    nome?: string;
    unidades?: IUnidade[];
}

export class Cidade implements ICidade {
    constructor(public id?: number, public nome?: string, public unidades?: IUnidade[]) {}
}
