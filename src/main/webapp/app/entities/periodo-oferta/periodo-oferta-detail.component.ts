import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';

@Component({
    selector: 'jhi-periodo-oferta-detail',
    templateUrl: './periodo-oferta-detail.component.html'
})
export class PeriodoOfertaDetailComponent implements OnInit {
    periodoOferta: IPeriodoOferta;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodoOferta }) => {
            this.periodoOferta = periodoOferta;
        });
    }

    previousState() {
        window.history.back();
    }
}
