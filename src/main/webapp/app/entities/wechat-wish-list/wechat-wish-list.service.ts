import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WechatWishList } from './wechat-wish-list.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatWishListService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-wish-lists';

    constructor(private http: Http) { }

    create(wechatWishList: WechatWishList): Observable<WechatWishList> {
        const copy = this.convert(wechatWishList);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatWishList: WechatWishList): Observable<WechatWishList> {
        const copy = this.convert(wechatWishList);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatWishList> {
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
     * Convert a returned JSON object to WechatWishList.
     */
    private convertItemFromServer(json: any): WechatWishList {
        const entity: WechatWishList = Object.assign(new WechatWishList(), json);
        return entity;
    }

    /**
     * Convert a WechatWishList to a JSON which can be sent to the server.
     */
    private convert(wechatWishList: WechatWishList): WechatWishList {
        const copy: WechatWishList = Object.assign({}, wechatWishList);
        return copy;
    }
}
