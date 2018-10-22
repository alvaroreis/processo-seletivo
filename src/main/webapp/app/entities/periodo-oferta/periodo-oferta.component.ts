import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';
import { Principal } from 'app/core';
import { PeriodoOfertaService } from './periodo-oferta.service';

@Component({
    selector: 'jhi-periodo-oferta',
    templateUrl: './periodo-oferta.component.html'
})
export class PeriodoOfertaComponent implements OnInit, OnDestroy {
    periodoOfertas: IPeriodoOferta[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private periodoOfertaService: PeriodoOfertaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.periodoOfertaService.query().subscribe(
            (res: HttpResponse<IPeriodoOferta[]>) => {
                this.periodoOfertas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodoOfertas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPeriodoOferta) {
        return item.id;
    }

    registerChangeInPeriodoOfertas() {
        this.eventSubscriber = this.eventManager.subscribe('periodoOfertaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
