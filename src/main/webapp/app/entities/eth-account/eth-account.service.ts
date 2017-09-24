import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EthAccount } from './eth-account.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EthAccountService {

    private resourceUrl = 'api/eth-accounts';

    constructor(private http: Http) { }

    create(ethAccount: EthAccount): Observable<EthAccount> {
        const copy = this.convert(ethAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(ethAccount: EthAccount): Observable<EthAccount> {
        const copy = this.convert(ethAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EthAccount> {
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

    private convert(ethAccount: EthAccount): EthAccount {
        const copy: EthAccount = Object.assign({}, ethAccount);
        return copy;
    }
}
