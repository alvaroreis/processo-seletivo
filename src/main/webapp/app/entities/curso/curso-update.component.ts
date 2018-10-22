import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';
import { IModalidade } from 'app/shared/model/modalidade.model';
import { ModalidadeService } from 'app/entities/modalidade';
import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from 'app/entities/turno';
import { IUnidade } from 'app/shared/model/unidade.model';
import { UnidadeService } from 'app/entities/unidade';

@Component({
    selector: 'jhi-curso-update',
    templateUrl: './curso-update.component.html'
})
export class CursoUpdateComponent implements OnInit {
    curso: ICurso;
    isSaving: boolean;

    modalidades: IModalidade[];

    turnos: ITurno[];

    unidades: IUnidade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cursoService: CursoService,
        private modalidadeService: ModalidadeService,
        private turnoService: TurnoService,
        private unidadeService: UnidadeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ curso }) => {
            this.curso = curso;
        });
        this.modalidadeService.query().subscribe(
            (res: HttpResponse<IModalidade[]>) => {
                this.modalidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.turnoService.query().subscribe(
            (res: HttpResponse<ITurno[]>) => {
                this.turnos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.curso.id !== undefined) {
            this.subscribeToSaveResponse(this.cursoService.update(this.curso));
        } else {
            this.subscribeToSaveResponse(this.cursoService.create(this.curso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICurso>>) {
        result.subscribe((res: HttpResponse<ICurso>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackModalidadeById(index: number, item: IModalidade) {
        return item.id;
    }

    trackTurnoById(index: number, item: ITurno) {
        return item.id;
    }

    trackUnidadeById(index: number, item: IUnidade) {
        return item.id;
    }
}
