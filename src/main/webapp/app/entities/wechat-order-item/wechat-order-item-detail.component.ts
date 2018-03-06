import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WechatOrderItem } from './wechat-order-item.model';
import { WechatOrderItemService } from './wechat-order-item.service';

@Component({
    selector: 'jhi-wechat-order-item-detail',
    templateUrl: './wechat-order-item-detail.component.html'
})
export class WechatOrderItemDetailComponent implements OnInit, OnDestroy {

    wechatOrderItem: WechatOrderItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wechatOrderItemService: WechatOrderItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatOrderItems();
    }

    load(id) {
        this.wechatOrderItemService.find(id).subscribe((wechatOrderItem) => {
            this.wechatOrderItem = wechatOrderItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatOrderItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatOrderItemListModification',
            (response) => this.load(this.wechatOrderItem.id)
        );
    }
}
