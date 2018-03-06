import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { A5BackendSharedModule } from '../../shared';
import {
    CollectListService,
    CollectListPopupService,
    CollectListComponent,
    CollectListDetailComponent,
    CollectListDialogComponent,
    CollectListPopupComponent,
    CollectListDeletePopupComponent,
    CollectListDeleteDialogComponent,
    collectListRoute,
    collectListPopupRoute,
    CollectListResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...collectListRoute,
    ...collectListPopupRoute,
];

@NgModule({
    imports: [
        A5BackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CollectListComponent,
        CollectListDetailComponent,
        CollectListDialogComponent,
        CollectListDeleteDialogComponent,
        CollectListPopupComponent,
        CollectListDeletePopupComponent,
    ],
    entryComponents: [
        CollectListComponent,
        CollectListDialogComponent,
        CollectListPopupComponent,
        CollectListDeleteDialogComponent,
        CollectListDeletePopupComponent,
    ],
    providers: [
        CollectListService,
        CollectListPopupService,
        CollectListResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class A5BackendCollectListModule {}
