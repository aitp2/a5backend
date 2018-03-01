import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { WechatUserPopupService } from './wechat-user-popup.service';
import { WechatUserService } from './wechat-user.service';
import { WechatWishList, WechatWishListService } from '../wechat-wish-list';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wechat-user-dialog',
    templateUrl: './wechat-user-dialog.component.html'
})
export class WechatUserDialogComponent implements OnInit {

    wechatUser: WechatUser;
    isSaving: boolean;

    wechatwishlists: WechatWishList[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private wechatUserService: WechatUserService,
        private wechatWishListService: WechatWishListService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatWishListService
            .query({filter: 'wechatuser-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.wechatUser.wechatWishListId) {
                    this.wechatwishlists = res.json;
                } else {
                    this.wechatWishListService
                        .find(this.wechatUser.wechatWishListId)
                        .subscribe((subRes: WechatWishList) => {
                            this.wechatwishlists = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatUserService.update(this.wechatUser));
        } else {
            this.subscribeToSaveResponse(
                this.wechatUserService.create(this.wechatUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatUser>) {
        result.subscribe((res: WechatUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatUser) {
        this.eventManager.broadcast({ name: 'wechatUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWechatWishListById(index: number, item: WechatWishList) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wechat-user-popup',
    template: ''
})
export class WechatUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatUserPopupService: WechatUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatUserPopupService
                    .open(WechatUserDialogComponent as Component, params['id']);
            } else {
                this.wechatUserPopupService
                    .open(WechatUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
