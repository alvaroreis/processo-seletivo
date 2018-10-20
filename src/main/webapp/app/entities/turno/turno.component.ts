import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { Principal } from 'app/core';
import { TurnoService } from './turno.service';

@Component({
    selector: 'jhi-turno',
    templateUrl: './turno.component.html'
})
export class TurnoComponent implements OnInit, OnDestroy {
    turnos: ITurno[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private turnoService: TurnoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.turnoService.query().subscribe(
            (res: HttpResponse<ITurno[]>) => {
                this.turnos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTurnos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITurno) {
        return item.id;
    }

    registerChangeInTurnos() {
        this.eventSubscriber = this.eventManager.subscribe('turnoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
