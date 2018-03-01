import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WechatProduct } from './wechat-product.model';
import { WechatProductService } from './wechat-product.service';

@Component({
    selector: 'jhi-wechat-product-detail',
    templateUrl: './wechat-product-detail.component.html'
})
export class WechatProductDetailComponent implements OnInit, OnDestroy {

    wechatProduct: WechatProduct;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wechatProductService: WechatProductService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatProducts();
    }

    load(id) {
        this.wechatProductService.find(id).subscribe((wechatProduct) => {
            this.wechatProduct = wechatProduct;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatProducts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatProductListModification',
            (response) => this.load(this.wechatProduct.id)
        );
    }
}
