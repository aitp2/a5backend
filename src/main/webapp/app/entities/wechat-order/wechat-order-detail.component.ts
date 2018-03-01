import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WechatOrder } from './wechat-order.model';
import { WechatOrderService } from './wechat-order.service';

@Component({
    selector: 'jhi-wechat-order-detail',
    templateUrl: './wechat-order-detail.component.html'
})
export class WechatOrderDetailComponent implements OnInit, OnDestroy {

    wechatOrder: WechatOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wechatOrderService: WechatOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatOrders();
    }

    load(id) {
        this.wechatOrderService.find(id).subscribe((wechatOrder) => {
            this.wechatOrder = wechatOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatOrderListModification',
            (response) => this.load(this.wechatOrder.id)
        );
    }
}
