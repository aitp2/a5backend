/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderItemDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item-dialog.component';
import { WechatOrderItemService } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.service';
import { WechatOrderItem } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.model';
import { WechatOrderService } from '../../../../../../main/webapp/app/entities/wechat-order';

describe('Component Tests', () => {

    describe('WechatOrderItem Management Dialog Component', () => {
        let comp: WechatOrderItemDialogComponent;
        let fixture: ComponentFixture<WechatOrderItemDialogComponent>;
        let service: WechatOrderItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderItemDialogComponent],
                providers: [
                    WechatOrderService,
                    WechatOrderItemService
                ]
            })
            .overrideTemplate(WechatOrderItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatOrderItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.wechatOrderItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatOrderItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatOrderItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.wechatOrderItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatOrderItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
