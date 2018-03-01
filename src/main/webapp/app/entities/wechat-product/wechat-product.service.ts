import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WechatProduct } from './wechat-product.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatProductService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-products';

    constructor(private http: Http) { }

    create(wechatProduct: WechatProduct): Observable<WechatProduct> {
        const copy = this.convert(wechatProduct);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatProduct: WechatProduct): Observable<WechatProduct> {
        const copy = this.convert(wechatProduct);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatProduct> {
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
     * Convert a returned JSON object to WechatProduct.
     */
    private convertItemFromServer(json: any): WechatProduct {
        const entity: WechatProduct = Object.assign(new WechatProduct(), json);
        return entity;
    }

    /**
     * Convert a WechatProduct to a JSON which can be sent to the server.
     */
    private convert(wechatProduct: WechatProduct): WechatProduct {
        const copy: WechatProduct = Object.assign({}, wechatProduct);
        return copy;
    }
}
