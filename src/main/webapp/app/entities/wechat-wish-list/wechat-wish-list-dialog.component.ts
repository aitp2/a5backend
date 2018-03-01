import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WechatWishList } from './wechat-wish-list.model';
import { WechatWishListPopupService } from './wechat-wish-list-popup.service';
import { WechatWishListService } from './wechat-wish-list.service';
import { WechatUser, WechatUserService } from '../wechat-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wechat-wish-list-dialog',
    templateUrl: './wechat-wish-list-dialog.component.html'
})
export class WechatWishListDialogComponent implements OnInit {

    wechatWishList: WechatWishList;
    isSaving: boolean;

    wechatusers: WechatUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private wechatWishListService: WechatWishListService,
        private wechatUserService: WechatUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatUserService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatWishList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatWishListService.update(this.wechatWishList));
        } else {
            this.subscribeToSaveResponse(
                this.wechatWishListService.create(this.wechatWishList));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatWishList>) {
        result.subscribe((res: WechatWishList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatWishList) {
        this.eventManager.broadcast({ name: 'wechatWishListListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-wechat-wish-list-popup',
    template: ''
})
export class WechatWishListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatWishListPopupService: WechatWishListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatWishListPopupService
                    .open(WechatWishListDialogComponent as Component, params['id']);
            } else {
                this.wechatWishListPopupService
                    .open(WechatWishListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
