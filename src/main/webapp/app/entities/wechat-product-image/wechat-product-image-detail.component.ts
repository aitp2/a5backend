import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { WechatProductImage } from './wechat-product-image.model';
import { WechatProductImageService } from './wechat-product-image.service';

@Component({
    selector: 'jhi-wechat-product-image-detail',
    templateUrl: './wechat-product-image-detail.component.html'
})
export class WechatProductImageDetailComponent implements OnInit, OnDestroy {

    wechatProductImage: WechatProductImage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private wechatProductImageService: WechatProductImageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatProductImages();
    }

    load(id) {
        this.wechatProductImageService.find(id).subscribe((wechatProductImage) => {
            this.wechatProductImage = wechatProductImage;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatProductImages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatProductImageListModification',
            (response) => this.load(this.wechatProductImage.id)
        );
    }
}
