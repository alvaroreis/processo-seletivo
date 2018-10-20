import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';

@Component({
    selector: 'jhi-cidade-update',
    templateUrl: './cidade-update.component.html'
})
export class CidadeUpdateComponent implements OnInit {
    cidade: ICidade;
    isSaving: boolean;

    constructor(private cidadeService: CidadeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cidade }) => {
            this.cidade = cidade;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cidade.id !== undefined) {
            this.subscribeToSaveResponse(this.cidadeService.update(this.cidade));
        } else {
            this.subscribeToSaveResponse(this.cidadeService.create(this.cidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICidade>>) {
        result.subscribe((res: HttpResponse<ICidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
