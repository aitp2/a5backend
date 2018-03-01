/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order-dialog.component';
import { WechatOrderService } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.service';
import { WechatOrder } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.model';

describe('Component Tests', () => {

    describe('WechatOrder Management Dialog Component', () => {
        let comp: WechatOrderDialogComponent;
        let fixture: ComponentFixture<WechatOrderDialogComponent>;
        let service: WechatOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderDialogComponent],
                providers: [
                    WechatOrderService
                ]
            })
            .overrideTemplate(WechatOrderDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatOrder(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.wechatOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatOrder();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.wechatOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
