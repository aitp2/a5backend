import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatProductImage } from './wechat-product-image.model';
import { WechatProductImagePopupService } from './wechat-product-image-popup.service';
import { WechatProductImageService } from './wechat-product-image.service';

@Component({
    selector: 'jhi-wechat-product-image-delete-dialog',
    templateUrl: './wechat-product-image-delete-dialog.component.html'
})
export class WechatProductImageDeleteDialogComponent {

    wechatProductImage: WechatProductImage;

    constructor(
        private wechatProductImageService: WechatProductImageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatProductImageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatProductImageListModification',
                content: 'Deleted an wechatProductImage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-product-image-delete-popup',
    template: ''
})
export class WechatProductImageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatProductImagePopupService: WechatProductImagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatProductImagePopupService
                .open(WechatProductImageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
