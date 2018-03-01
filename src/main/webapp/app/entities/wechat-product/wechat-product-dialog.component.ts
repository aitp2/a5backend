import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WechatProduct } from './wechat-product.model';
import { WechatProductPopupService } from './wechat-product-popup.service';
import { WechatProductService } from './wechat-product.service';
import { WechatUser, WechatUserService } from '../wechat-user';
import { WechatWishList, WechatWishListService } from '../wechat-wish-list';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wechat-product-dialog',
    templateUrl: './wechat-product-dialog.component.html'
})
export class WechatProductDialogComponent implements OnInit {

    wechatProduct: WechatProduct;
    isSaving: boolean;

    wechatusers: WechatUser[];

    wechatwishlists: WechatWishList[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private wechatProductService: WechatProductService,
        private wechatUserService: WechatUserService,
        private wechatWishListService: WechatWishListService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatUserService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.wechatWishListService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatwishlists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatProduct.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatProductService.update(this.wechatProduct));
        } else {
            this.subscribeToSaveResponse(
                this.wechatProductService.create(this.wechatProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatProduct>) {
        result.subscribe((res: WechatProduct) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatProduct) {
        this.eventManager.broadcast({ name: 'wechatProductListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWechatUserById(index: number, item: WechatUser) {
        return item.id;
    }

    trackWechatWishListById(index: number, item: WechatWishList) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wechat-product-popup',
    template: ''
})
export class WechatProductPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatProductPopupService: WechatProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatProductPopupService
                    .open(WechatProductDialogComponent as Component, params['id']);
            } else {
                this.wechatProductPopupService
                    .open(WechatProductDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
