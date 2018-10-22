import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';
import { PeriodoOfertaService } from './periodo-oferta.service';
import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';
import { DemandaCursoService } from 'app/entities/demanda-curso';

@Component({
    selector: 'jhi-periodo-oferta-update',
    templateUrl: './periodo-oferta-update.component.html'
})
export class PeriodoOfertaUpdateComponent implements OnInit {
    periodoOferta: IPeriodoOferta;
    isSaving: boolean;

    demandacursos: IDemandaCurso[];
    dataInicioDp: any;
    dataFimDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private periodoOfertaService: PeriodoOfertaService,
        private demandaCursoService: DemandaCursoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ periodoOferta }) => {
            this.periodoOferta = periodoOferta;
        });
        this.demandaCursoService.query().subscribe(
            (res: HttpResponse<IDemandaCurso[]>) => {
                this.demandacursos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.periodoOferta.id !== undefined) {
            this.subscribeToSaveResponse(this.periodoOfertaService.update(this.periodoOferta));
        } else {
            this.subscribeToSaveResponse(this.periodoOfertaService.create(this.periodoOferta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoOferta>>) {
        result.subscribe((res: HttpResponse<IPeriodoOferta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDemandaCursoById(index: number, item: IDemandaCurso) {
        return item.id;
    }
}
