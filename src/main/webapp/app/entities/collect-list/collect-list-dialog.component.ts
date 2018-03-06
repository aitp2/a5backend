import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollectList } from './collect-list.model';
import { CollectListPopupService } from './collect-list-popup.service';
import { CollectListService } from './collect-list.service';
import { WechatProduct, WechatProductService } from '../wechat-product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collect-list-dialog',
    templateUrl: './collect-list-dialog.component.html'
})
export class CollectListDialogComponent implements OnInit {

    collectList: CollectList;
    isSaving: boolean;

    wechatproducts: WechatProduct[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private collectListService: CollectListService,
        private wechatProductService: WechatProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wechatProductService.query()
            .subscribe((res: ResponseWrapper) => { this.wechatproducts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collectList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collectListService.update(this.collectList));
        } else {
            this.subscribeToSaveResponse(
                this.collectListService.create(this.collectList));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollectList>) {
        result.subscribe((res: CollectList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CollectList) {
        this.eventManager.broadcast({ name: 'collectListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWechatProductById(index: number, item: WechatProduct) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-collect-list-popup',
    template: ''
})
export class CollectListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collectListPopupService: CollectListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.collectListPopupService
                    .open(CollectListDialogComponent as Component, params['id']);
            } else {
                this.collectListPopupService
                    .open(CollectListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
