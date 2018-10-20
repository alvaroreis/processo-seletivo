import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurno } from 'app/shared/model/turno.model';

type EntityResponseType = HttpResponse<ITurno>;
type EntityArrayResponseType = HttpResponse<ITurno[]>;

@Injectable({ providedIn: 'root' })
export class TurnoService {
    public resourceUrl = SERVER_API_URL + 'api/turnos';

    constructor(private http: HttpClient) {}

    create(turno: ITurno): Observable<EntityResponseType> {
        return this.http.post<ITurno>(this.resourceUrl, turno, { observe: 'response' });
    }

    update(turno: ITurno): Observable<EntityResponseType> {
        return this.http.put<ITurno>(this.resourceUrl, turno, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITurno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITurno[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
