import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Booking } from './booking.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BookingService {

    private resourceUrl = 'api/bookings';

    constructor(private http: Http) { }

    create(booking: Booking): Observable<Booking> {
        const copy = this.convert(booking);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(booking: Booking): Observable<Booking> {
        const copy = this.convert(booking);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Booking> {
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

    private convert(booking: Booking): Booking {
        const copy: Booking = Object.assign({}, booking);
        return copy;
    }
}
