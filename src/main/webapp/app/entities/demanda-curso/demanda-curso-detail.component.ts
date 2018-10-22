import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';

@Component({
    selector: 'jhi-demanda-curso-detail',
    templateUrl: './demanda-curso-detail.component.html'
})
export class DemandaCursoDetailComponent implements OnInit {
    demandaCurso: IDemandaCurso;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandaCurso }) => {
            this.demandaCurso = demandaCurso;
        });
    }

    previousState() {
        window.history.back();
    }
}
