import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WechatProductImage } from './wechat-product-image.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatProductImageService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-product-images';

    constructor(private http: Http) { }

    create(wechatProductImage: WechatProductImage): Observable<WechatProductImage> {
        const copy = this.convert(wechatProductImage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatProductImage: WechatProductImage): Observable<WechatProductImage> {
        const copy = this.convert(wechatProductImage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatProductImage> {
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
     * Convert a returned JSON object to WechatProductImage.
     */
    private convertItemFromServer(json: any): WechatProductImage {
        const entity: WechatProductImage = Object.assign(new WechatProductImage(), json);
        return entity;
    }

    /**
     * Convert a WechatProductImage to a JSON which can be sent to the server.
     */
    private convert(wechatProductImage: WechatProductImage): WechatProductImage {
        const copy: WechatProductImage = Object.assign({}, wechatProductImage);
        return copy;
    }
}
