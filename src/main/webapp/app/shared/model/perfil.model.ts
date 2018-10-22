import { IUsuario } from 'app/shared/model//usuario.model';

export interface IPerfil {
    id?: number;
    nome?: string;
    usuarios?: IUsuario[];
}

export class Perfil implements IPerfil {
    constructor(public id?: number, public nome?: string, public usuarios?: IUsuario[]) {}
}
