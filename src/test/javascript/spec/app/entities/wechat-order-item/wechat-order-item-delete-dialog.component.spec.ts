/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderItemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item-delete-dialog.component';
import { WechatOrderItemService } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.service';

describe('Component Tests', () => {

    describe('WechatOrderItem Management Delete Component', () => {
        let comp: WechatOrderItemDeleteDialogComponent;
        let fixture: ComponentFixture<WechatOrderItemDeleteDialogComponent>;
        let service: WechatOrderItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderItemDeleteDialogComponent],
                providers: [
                    WechatOrderItemService
                ]
            })
            .overrideTemplate(WechatOrderItemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
