import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollectList } from './collect-list.model';
import { CollectListPopupService } from './collect-list-popup.service';
import { CollectListService } from './collect-list.service';

@Component({
    selector: 'jhi-collect-list-delete-dialog',
    templateUrl: './collect-list-delete-dialog.component.html'
})
export class CollectListDeleteDialogComponent {

    collectList: CollectList;

    constructor(
        private collectListService: CollectListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collectListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collectListListModification',
                content: 'Deleted an collectList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collect-list-delete-popup',
    template: ''
})
export class CollectListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collectListPopupService: CollectListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.collectListPopupService
                .open(CollectListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
