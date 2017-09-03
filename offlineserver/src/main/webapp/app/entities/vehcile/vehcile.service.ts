import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Vehcile } from './vehcile.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehcileService {

    private resourceUrl = 'api/vehciles';

    constructor(private http: Http) { }

    create(vehcile: Vehcile): Observable<Vehcile> {
        const copy = this.convert(vehcile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(vehcile: Vehcile): Observable<Vehcile> {
        const copy = this.convert(vehcile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Vehcile> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(vehcile: Vehcile): Vehcile {
        const copy: Vehcile = Object.assign({}, vehcile);
        return copy;
    }
}
