import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CollectList } from './collect-list.model';
import { CollectListService } from './collect-list.service';

@Component({
    selector: 'jhi-collect-list-detail',
    templateUrl: './collect-list-detail.component.html'
})
export class CollectListDetailComponent implements OnInit, OnDestroy {

    collectList: CollectList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collectListService: CollectListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollectLists();
    }

    load(id) {
        this.collectListService.find(id).subscribe((collectList) => {
            this.collectList = collectList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollectLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collectListListModification',
            (response) => this.load(this.collectList.id)
        );
    }
}
