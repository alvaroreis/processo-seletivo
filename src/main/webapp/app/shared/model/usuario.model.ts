import { IUnidade } from 'app/shared/model//unidade.model';
import { IPerfil } from 'app/shared/model//perfil.model';

export interface IUsuario {
    id?: number;
    login?: string;
    senha?: string;
    unidade?: IUnidade;
    perfil?: IPerfil;
}

export class Usuario implements IUsuario {
    constructor(public id?: number, public login?: string, public senha?: string, public unidade?: IUnidade, public perfil?: IPerfil) {}
}
