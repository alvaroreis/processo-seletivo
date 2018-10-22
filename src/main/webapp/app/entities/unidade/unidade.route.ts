import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Unidade } from 'app/shared/model/unidade.model';
import { UnidadeService } from './unidade.service';
import { UnidadeComponent } from './unidade.component';
import { UnidadeDetailComponent } from './unidade-detail.component';
import { UnidadeUpdateComponent } from './unidade-update.component';
import { UnidadeDeletePopupComponent } from './unidade-delete-dialog.component';
import { IUnidade } from 'app/shared/model/unidade.model';

@Injectable({ providedIn: 'root' })
export class UnidadeResolve implements Resolve<IUnidade> {
    constructor(private service: UnidadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unidade: HttpResponse<Unidade>) => unidade.body));
        }
        return of(new Unidade());
    }
}

export const unidadeRoute: Routes = [
    {
        path: 'unidade',
        component: UnidadeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.unidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidade/:id/view',
        component: UnidadeDetailComponent,
        resolve: {
            unidade: UnidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.unidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidade/new',
        component: UnidadeUpdateComponent,
        resolve: {
            unidade: UnidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.unidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidade/:id/edit',
        component: UnidadeUpdateComponent,
        resolve: {
            unidade: UnidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.unidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unidadePopupRoute: Routes = [
    {
        path: 'unidade/:id/delete',
        component: UnidadeDeletePopupComponent,
        resolve: {
            unidade: UnidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.unidade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
