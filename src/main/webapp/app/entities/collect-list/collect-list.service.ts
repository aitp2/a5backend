import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CollectList } from './collect-list.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollectListService {

    private resourceUrl =  SERVER_API_URL + 'api/collect-lists';

    constructor(private http: Http) { }

    create(collectList: CollectList): Observable<CollectList> {
        const copy = this.convert(collectList);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(collectList: CollectList): Observable<CollectList> {
        const copy = this.convert(collectList);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CollectList> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CollectList.
     */
    private convertItemFromServer(json: any): CollectList {
        const entity: CollectList = Object.assign(new CollectList(), json);
        return entity;
    }

    /**
     * Convert a CollectList to a JSON which can be sent to the server.
     */
    private convert(collectList: CollectList): CollectList {
        const copy: CollectList = Object.assign({}, collectList);
        return copy;
    }
}
