import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriodoOferta } from 'app/shared/model/periodo-oferta.model';

type EntityResponseType = HttpResponse<IPeriodoOferta>;
type EntityArrayResponseType = HttpResponse<IPeriodoOferta[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoOfertaService {
    public resourceUrl = SERVER_API_URL + 'api/periodo-ofertas';

    constructor(private http: HttpClient) {}

    create(periodoOferta: IPeriodoOferta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodoOferta);
        return this.http
            .post<IPeriodoOferta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(periodoOferta: IPeriodoOferta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodoOferta);
        return this.http
            .put<IPeriodoOferta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPeriodoOferta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPeriodoOferta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(periodoOferta: IPeriodoOferta): IPeriodoOferta {
        const copy: IPeriodoOferta = Object.assign({}, periodoOferta, {
            dataInicio:
                periodoOferta.dataInicio != null && periodoOferta.dataInicio.isValid()
                    ? periodoOferta.dataInicio.format(DATE_FORMAT)
                    : null,
            dataFim: periodoOferta.dataFim != null && periodoOferta.dataFim.isValid() ? periodoOferta.dataFim.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataInicio = res.body.dataInicio != null ? moment(res.body.dataInicio) : null;
        res.body.dataFim = res.body.dataFim != null ? moment(res.body.dataFim) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((periodoOferta: IPeriodoOferta) => {
            periodoOferta.dataInicio = periodoOferta.dataInicio != null ? moment(periodoOferta.dataInicio) : null;
            periodoOferta.dataFim = periodoOferta.dataFim != null ? moment(periodoOferta.dataFim) : null;
        });
        return res;
    }
}
