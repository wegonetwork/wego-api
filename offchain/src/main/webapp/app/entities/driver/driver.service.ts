import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Driver } from './driver.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DriverService {

    private resourceUrl = 'api/drivers';

    constructor(private http: Http) { }

    create(driver: Driver): Observable<Driver> {
        const copy = this.convert(driver);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(driver: Driver): Observable<Driver> {
        const copy = this.convert(driver);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Driver> {
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

    private convert(driver: Driver): Driver {
        const copy: Driver = Object.assign({}, driver);
        return copy;
    }
}
