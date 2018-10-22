import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnidade } from 'app/shared/model/unidade.model';

@Component({
    selector: 'jhi-unidade-detail',
    templateUrl: './unidade-detail.component.html'
})
export class UnidadeDetailComponent implements OnInit {
    unidade: IUnidade;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unidade }) => {
            this.unidade = unidade;
        });
    }

    previousState() {
        window.history.back();
    }
}
