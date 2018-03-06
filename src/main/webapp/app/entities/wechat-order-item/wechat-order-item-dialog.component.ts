import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WechatOrderItem } from './wechat-order-item.model';
import { WechatOrderItemPopupService } from './wechat-order-item-popup.service';
import { WechatOrderItemService } from './wechat-order-item.service';
import { WechatOrder, WechatOrderService } from '../wechat-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wechat-order-item-dialog',
    templateUrl: './wechat-order-item-dialog.component.html'
})
export class WechatOrderItemDialogComponent implements OnInit {

    wechatOrderItem: WechatOrderItem;
    isSaving: boolean;

    wechatorders: WechatOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private wechatOrderItemService: WechatOrderItemService,
        private wechatOrderService: WechatOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatOrderItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatOrderItemService.update(this.wechatOrderItem));
        } else {
            this.subscribeToSaveResponse(
                this.wechatOrderItemService.create(this.wechatOrderItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatOrderItem>) {
        result.subscribe((res: WechatOrderItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatOrderItem) {
        this.eventManager.broadcast({ name: 'wechatOrderItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWechatOrderById(index: number, item: WechatOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wechat-order-item-popup',
    template: ''
})
export class WechatOrderItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatOrderItemPopupService: WechatOrderItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatOrderItemPopupService
                    .open(WechatOrderItemDialogComponent as Component, params['id']);
            } else {
                this.wechatOrderItemPopupService
                    .open(WechatOrderItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
