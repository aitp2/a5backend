import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WechatOrderItem } from './wechat-order-item.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatOrderItemService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-order-items';

    constructor(private http: Http) { }

    create(wechatOrderItem: WechatOrderItem): Observable<WechatOrderItem> {
        const copy = this.convert(wechatOrderItem);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatOrderItem: WechatOrderItem): Observable<WechatOrderItem> {
        const copy = this.convert(wechatOrderItem);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatOrderItem> {
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
     * Convert a returned JSON object to WechatOrderItem.
     */
    private convertItemFromServer(json: any): WechatOrderItem {
        const entity: WechatOrderItem = Object.assign(new WechatOrderItem(), json);
        return entity;
    }

    /**
     * Convert a WechatOrderItem to a JSON which can be sent to the server.
     */
    private convert(wechatOrderItem: WechatOrderItem): WechatOrderItem {
        const copy: WechatOrderItem = Object.assign({}, wechatOrderItem);
        return copy;
    }
}
