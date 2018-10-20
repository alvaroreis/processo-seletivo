import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from './turno.service';

@Component({
    selector: 'jhi-turno-update',
    templateUrl: './turno-update.component.html'
})
export class TurnoUpdateComponent implements OnInit {
    turno: ITurno;
    isSaving: boolean;

    constructor(private turnoService: TurnoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ turno }) => {
            this.turno = turno;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.turno.id !== undefined) {
            this.subscribeToSaveResponse(this.turnoService.update(this.turno));
        } else {
            this.subscribeToSaveResponse(this.turnoService.create(this.turno));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITurno>>) {
        result.subscribe((res: HttpResponse<ITurno>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
