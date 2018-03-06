import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { WechatProductImage } from './wechat-product-image.model';
import { WechatProductImagePopupService } from './wechat-product-image-popup.service';
import { WechatProductImageService } from './wechat-product-image.service';
import { WechatProduct, WechatProductService } from '../wechat-product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wechat-product-image-dialog',
    templateUrl: './wechat-product-image-dialog.component.html'
})
export class WechatProductImageDialogComponent implements OnInit {

    wechatProductImage: WechatProductImage;
    isSaving: boolean;

    wechatproducts: WechatProduct[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private wechatProductImageService: WechatProductImageService,
        private wechatProductService: WechatProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatProductService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatproducts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatProductImage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatProductImageService.update(this.wechatProductImage));
        } else {
            this.subscribeToSaveResponse(
                this.wechatProductImageService.create(this.wechatProductImage));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatProductImage>) {
        result.subscribe((res: WechatProductImage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatProductImage) {
        this.eventManager.broadcast({ name: 'wechatProductImageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWechatProductById(index: number, item: WechatProduct) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wechat-product-image-popup',
    template: ''
})
export class WechatProductImagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatProductImagePopupService: WechatProductImagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatProductImagePopupService
                    .open(WechatProductImageDialogComponent as Component, params['id']);
            } else {
                this.wechatProductImagePopupService
                    .open(WechatProductImageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
