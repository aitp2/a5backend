import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatOrder } from './wechat-order.model';
import { WechatOrderPopupService } from './wechat-order-popup.service';
import { WechatOrderService } from './wechat-order.service';

@Component({
    selector: 'jhi-wechat-order-dialog',
    templateUrl: './wechat-order-dialog.component.html'
})
export class WechatOrderDialogComponent implements OnInit {

    wechatOrder: WechatOrder;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private wechatOrderService: WechatOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatOrderService.update(this.wechatOrder));
        } else {
            this.subscribeToSaveResponse(
                this.wechatOrderService.create(this.wechatOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatOrder>) {
        result.subscribe((res: WechatOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatOrder) {
        this.eventManager.broadcast({ name: 'wechatOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-wechat-order-popup',
    template: ''
})
export class WechatOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatOrderPopupService: WechatOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatOrderPopupService
                    .open(WechatOrderDialogComponent as Component, params['id']);
            } else {
                this.wechatOrderPopupService
                    .open(WechatOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
