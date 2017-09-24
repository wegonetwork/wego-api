import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RideRequest } from './ride-request.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RideRequestService {

    private resourceUrl = 'api/ride-requests';

    constructor(private http: Http) { }

    create(rideRequest: RideRequest): Observable<RideRequest> {
        const copy = this.convert(rideRequest);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(rideRequest: RideRequest): Observable<RideRequest> {
        const copy = this.convert(rideRequest);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RideRequest> {
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

    private convert(rideRequest: RideRequest): RideRequest {
        const copy: RideRequest = Object.assign({}, rideRequest);
        return copy;
    }
}
