import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatOrderItem } from './wechat-order-item.model';
import { WechatOrderItemPopupService } from './wechat-order-item-popup.service';
import { WechatOrderItemService } from './wechat-order-item.service';

@Component({
    selector: 'jhi-wechat-order-item-delete-dialog',
    templateUrl: './wechat-order-item-delete-dialog.component.html'
})
export class WechatOrderItemDeleteDialogComponent {

    wechatOrderItem: WechatOrderItem;

    constructor(
        private wechatOrderItemService: WechatOrderItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatOrderItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatOrderItemListModification',
                content: 'Deleted an wechatOrderItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-order-item-delete-popup',
    template: ''
})
export class WechatOrderItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatOrderItemPopupService: WechatOrderItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatOrderItemPopupService
                .open(WechatOrderItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
