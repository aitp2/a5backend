import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatOrder } from './wechat-order.model';
import { WechatOrderPopupService } from './wechat-order-popup.service';
import { WechatOrderService } from './wechat-order.service';

@Component({
    selector: 'jhi-wechat-order-delete-dialog',
    templateUrl: './wechat-order-delete-dialog.component.html'
})
export class WechatOrderDeleteDialogComponent {

    wechatOrder: WechatOrder;

    constructor(
        private wechatOrderService: WechatOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatOrderListModification',
                content: 'Deleted an wechatOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-order-delete-popup',
    template: ''
})
export class WechatOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatOrderPopupService: WechatOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatOrderPopupService
                .open(WechatOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
