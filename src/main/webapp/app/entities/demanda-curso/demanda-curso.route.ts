import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DemandaCurso } from 'app/shared/model/demanda-curso.model';
import { DemandaCursoService } from './demanda-curso.service';
import { DemandaCursoComponent } from './demanda-curso.component';
import { DemandaCursoDetailComponent } from './demanda-curso-detail.component';
import { DemandaCursoUpdateComponent } from './demanda-curso-update.component';
import { DemandaCursoDeletePopupComponent } from './demanda-curso-delete-dialog.component';
import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';

@Injectable({ providedIn: 'root' })
export class DemandaCursoResolve implements Resolve<IDemandaCurso> {
    constructor(private service: DemandaCursoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((demandaCurso: HttpResponse<DemandaCurso>) => demandaCurso.body));
        }
        return of(new DemandaCurso());
    }
}

export const demandaCursoRoute: Routes = [
    {
        path: 'demanda-curso',
        component: DemandaCursoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.demandaCurso.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demanda-curso/:id/view',
        component: DemandaCursoDetailComponent,
        resolve: {
            demandaCurso: DemandaCursoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.demandaCurso.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demanda-curso/new',
        component: DemandaCursoUpdateComponent,
        resolve: {
            demandaCurso: DemandaCursoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.demandaCurso.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demanda-curso/:id/edit',
        component: DemandaCursoUpdateComponent,
        resolve: {
            demandaCurso: DemandaCursoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.demandaCurso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const demandaCursoPopupRoute: Routes = [
    {
        path: 'demanda-curso/:id/delete',
        component: DemandaCursoDeletePopupComponent,
        resolve: {
            demandaCurso: DemandaCursoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.demandaCurso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
