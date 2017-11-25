import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Rating } from './rating.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RatingService {

    private resourceUrl = 'api/ratings';

    constructor(private http: Http) { }

    create(rating: Rating): Observable<Rating> {
        const copy = this.convert(rating);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(rating: Rating): Observable<Rating> {
        const copy = this.convert(rating);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Rating> {
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

    private convert(rating: Rating): Rating {
        const copy: Rating = Object.assign({}, rating);
        return copy;
    }
}
