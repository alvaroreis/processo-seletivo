import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUnidade } from 'app/shared/model/unidade.model';
import { UnidadeService } from './unidade.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
    selector: 'jhi-unidade-update',
    templateUrl: './unidade-update.component.html'
})
export class UnidadeUpdateComponent implements OnInit {
    unidade: IUnidade;
    isSaving: boolean;

    usuarios: IUsuario[];

    cidades: ICidade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private unidadeService: UnidadeService,
        private usuarioService: UsuarioService,
        private cidadeService: CidadeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unidade }) => {
            this.unidade = unidade;
        });
        this.usuarioService.query({ filter: 'unidade(nome)-is-null' }).subscribe(
            (res: HttpResponse<IUsuario[]>) => {
                if (!this.unidade.usuario || !this.unidade.usuario.id) {
                    this.usuarios = res.body;
                } else {
                    this.usuarioService.find(this.unidade.usuario.id).subscribe(
                        (subRes: HttpResponse<IUsuario>) => {
                            this.usuarios = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cidadeService.query().subscribe(
            (res: HttpResponse<ICidade[]>) => {
                this.cidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unidade.id !== undefined) {
            this.subscribeToSaveResponse(this.unidadeService.update(this.unidade));
        } else {
            this.subscribeToSaveResponse(this.unidadeService.create(this.unidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnidade>>) {
        result.subscribe((res: HttpResponse<IUnidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUsuarioById(index: number, item: IUsuario) {
        return item.id;
    }

    trackCidadeById(index: number, item: ICidade) {
        return item.id;
    }
}
