import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatProduct } from './wechat-product.model';
import { WechatProductPopupService } from './wechat-product-popup.service';
import { WechatProductService } from './wechat-product.service';

@Component({
    selector: 'jhi-wechat-product-delete-dialog',
    templateUrl: './wechat-product-delete-dialog.component.html'
})
export class WechatProductDeleteDialogComponent {

    wechatProduct: WechatProduct;

    constructor(
        private wechatProductService: WechatProductService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatProductListModification',
                content: 'Deleted an wechatProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-product-delete-popup',
    template: ''
})
export class WechatProductDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatProductPopupService: WechatProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatProductPopupService
                .open(WechatProductDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
