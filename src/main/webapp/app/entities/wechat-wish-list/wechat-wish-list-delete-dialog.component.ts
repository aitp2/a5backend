import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatWishList } from './wechat-wish-list.model';
import { WechatWishListPopupService } from './wechat-wish-list-popup.service';
import { WechatWishListService } from './wechat-wish-list.service';

@Component({
    selector: 'jhi-wechat-wish-list-delete-dialog',
    templateUrl: './wechat-wish-list-delete-dialog.component.html'
})
export class WechatWishListDeleteDialogComponent {

    wechatWishList: WechatWishList;

    constructor(
        private wechatWishListService: WechatWishListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatWishListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatWishListListModification',
                content: 'Deleted an wechatWishList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-wish-list-delete-popup',
    template: ''
})
export class WechatWishListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatWishListPopupService: WechatWishListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatWishListPopupService
                .open(WechatWishListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
