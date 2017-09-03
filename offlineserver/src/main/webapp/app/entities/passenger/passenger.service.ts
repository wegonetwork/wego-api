import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Passenger } from './passenger.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PassengerService {

    private resourceUrl = 'api/passengers';

    constructor(private http: Http) { }

    create(passenger: Passenger): Observable<Passenger> {
        const copy = this.convert(passenger);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(passenger: Passenger): Observable<Passenger> {
        const copy = this.convert(passenger);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Passenger> {
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

    private convert(passenger: Passenger): Passenger {
        const copy: Passenger = Object.assign({}, passenger);
        return copy;
    }
}
