import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';
import { DemandaCursoService } from './demanda-curso.service';
import { IUnidade } from 'app/shared/model/unidade.model';
import { UnidadeService } from 'app/entities/unidade';

@Component({
    selector: 'jhi-demanda-curso-update',
    templateUrl: './demanda-curso-update.component.html'
})
export class DemandaCursoUpdateComponent implements OnInit {
    demandaCurso: IDemandaCurso;
    isSaving: boolean;

    unidades: IUnidade[];
    dataDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private demandaCursoService: DemandaCursoService,
        private unidadeService: UnidadeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ demandaCurso }) => {
            this.demandaCurso = demandaCurso;
        });
        this.unidadeService.query().subscribe(
            (res: HttpResponse<IUnidade[]>) => {
                this.unidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.demandaCurso.id !== undefined) {
            this.subscribeToSaveResponse(this.demandaCursoService.update(this.demandaCurso));
        } else {
            this.subscribeToSaveResponse(this.demandaCursoService.create(this.demandaCurso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDemandaCurso>>) {
        result.subscribe((res: HttpResponse<IDemandaCurso>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnidadeById(index: number, item: IUnidade) {
        return item.id;
    }
}
