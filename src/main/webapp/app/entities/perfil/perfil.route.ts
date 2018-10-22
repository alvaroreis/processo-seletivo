import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';
import { PerfilComponent } from './perfil.component';
import { PerfilDetailComponent } from './perfil-detail.component';
import { PerfilUpdateComponent } from './perfil-update.component';
import { PerfilDeletePopupComponent } from './perfil-delete-dialog.component';
import { IPerfil } from 'app/shared/model/perfil.model';

@Injectable({ providedIn: 'root' })
export class PerfilResolve implements Resolve<IPerfil> {
    constructor(private service: PerfilService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((perfil: HttpResponse<Perfil>) => perfil.body));
        }
        return of(new Perfil());
    }
}

export const perfilRoute: Routes = [
    {
        path: 'perfil',
        component: PerfilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.perfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'perfil/:id/view',
        component: PerfilDetailComponent,
        resolve: {
            perfil: PerfilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.perfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'perfil/new',
        component: PerfilUpdateComponent,
        resolve: {
            perfil: PerfilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.perfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'perfil/:id/edit',
        component: PerfilUpdateComponent,
        resolve: {
            perfil: PerfilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.perfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const perfilPopupRoute: Routes = [
    {
        path: 'perfil/:id/delete',
        component: PerfilDeletePopupComponent,
        resolve: {
            perfil: PerfilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'processoSeletivoApp.perfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
