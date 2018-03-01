import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WechatOrder } from './wechat-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-orders';

    constructor(private http: Http) { }

    create(wechatOrder: WechatOrder): Observable<WechatOrder> {
        const copy = this.convert(wechatOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatOrder: WechatOrder): Observable<WechatOrder> {
        const copy = this.convert(wechatOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatOrder> {
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
     * Convert a returned JSON object to WechatOrder.
     */
    private convertItemFromServer(json: any): WechatOrder {
        const entity: WechatOrder = Object.assign(new WechatOrder(), json);
        return entity;
    }

    /**
     * Convert a WechatOrder to a JSON which can be sent to the server.
     */
    private convert(wechatOrder: WechatOrder): WechatOrder {
        const copy: WechatOrder = Object.assign({}, wechatOrder);
        return copy;
    }
}
