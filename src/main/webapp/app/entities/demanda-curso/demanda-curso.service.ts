import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDemandaCurso } from 'app/shared/model/demanda-curso.model';

type EntityResponseType = HttpResponse<IDemandaCurso>;
type EntityArrayResponseType = HttpResponse<IDemandaCurso[]>;

@Injectable({ providedIn: 'root' })
export class DemandaCursoService {
    public resourceUrl = SERVER_API_URL + 'api/demanda-cursos';

    constructor(private http: HttpClient) {}

    create(demandaCurso: IDemandaCurso): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(demandaCurso);
        return this.http
            .post<IDemandaCurso>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(demandaCurso: IDemandaCurso): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(demandaCurso);
        return this.http
            .put<IDemandaCurso>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDemandaCurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDemandaCurso[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(demandaCurso: IDemandaCurso): IDemandaCurso {
        const copy: IDemandaCurso = Object.assign({}, demandaCurso, {
            data: demandaCurso.data != null && demandaCurso.data.isValid() ? demandaCurso.data.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.data = res.body.data != null ? moment(res.body.data) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((demandaCurso: IDemandaCurso) => {
            demandaCurso.data = demandaCurso.data != null ? moment(demandaCurso.data) : null;
        });
        return res;
    }
}
