import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PeriodoOferta } from 'app/shared/model/periodo-oferta.model';
import { PeriodoOfertaService } from './periodo-oferta.service';
import { PeriodoOfertaComponent } from './periodo-oferta.component';
import { PeriodoOfertaDetailComponent } from './periodo-oferta-detail.component';
import { PeriodoOfertaUpdateComponent } from './periodo-oferta-update.component';
import { PeriodoOfertaDeletePopupComponent } from './periodo-oferta-delete-dialog.component';
import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';

@Injectable({ providedIn: 'root' })
export class PeriodoOfertaResolve implements Resolve<IPeriodoOferta> {
    constructor(private service: PeriodoOfertaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((periodoOferta: HttpResponse<PeriodoOferta>) => periodoOferta.body));
        }
        return of(new PeriodoOferta());
    }
}

export const periodoOfertaRoute: Routes = [
    {
        path: 'periodo-oferta',
        component: PeriodoOfertaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.periodoOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-oferta/:id/view',
        component: PeriodoOfertaDetailComponent,
        resolve: {
            periodoOferta: PeriodoOfertaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.periodoOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-oferta/new',
        component: PeriodoOfertaUpdateComponent,
        resolve: {
            periodoOferta: PeriodoOfertaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.periodoOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-oferta/:id/edit',
        component: PeriodoOfertaUpdateComponent,
        resolve: {
            periodoOferta: PeriodoOfertaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.periodoOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodoOfertaPopupRoute: Routes = [
    {
        path: 'periodo-oferta/:id/delete',
        component: PeriodoOfertaDeletePopupComponent,
        resolve: {
            periodoOferta: PeriodoOfertaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.periodoOferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
