/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { CollectListDialogComponent } from '../../../../../../main/webapp/app/entities/collect-list/collect-list-dialog.component';
import { CollectListService } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.service';
import { CollectList } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.model';
import { WechatProductService } from '../../../../../../main/webapp/app/entities/wechat-product';

describe('Component Tests', () => {

    describe('CollectList Management Dialog Component', () => {
        let comp: CollectListDialogComponent;
        let fixture: ComponentFixture<CollectListDialogComponent>;
        let service: CollectListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [CollectListDialogComponent],
                providers: [
                    WechatProductService,
                    CollectListService
                ]
            })
            .overrideTemplate(CollectListDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollectListDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollectListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CollectList(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.collectList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'collectListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CollectList();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.collectList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'collectListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
