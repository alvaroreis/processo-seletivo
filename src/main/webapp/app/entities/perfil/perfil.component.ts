import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPerfil } from 'app/shared/model/perfil.model';
import { Principal } from 'app/core';
import { PerfilService } from './perfil.service';

@Component({
    selector: 'jhi-perfil',
    templateUrl: './perfil.component.html'
})
export class PerfilComponent implements OnInit, OnDestroy {
    perfils: IPerfil[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private perfilService: PerfilService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.perfilService.query().subscribe(
            (res: HttpResponse<IPerfil[]>) => {
                this.perfils = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPerfils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPerfil) {
        return item.id;
    }

    registerChangeInPerfils() {
        this.eventSubscriber = this.eventManager.subscribe('perfilListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
