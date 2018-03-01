import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WechatWishList } from './wechat-wish-list.model';
import { WechatWishListService } from './wechat-wish-list.service';

@Component({
    selector: 'jhi-wechat-wish-list-detail',
    templateUrl: './wechat-wish-list-detail.component.html'
})
export class WechatWishListDetailComponent implements OnInit, OnDestroy {

    wechatWishList: WechatWishList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wechatWishListService: WechatWishListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatWishLists();
    }

    load(id) {
        this.wechatWishListService.find(id).subscribe((wechatWishList) => {
            this.wechatWishList = wechatWishList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatWishLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatWishListListModification',
            (response) => this.load(this.wechatWishList.id)
        );
    }
}
