/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductImageDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image-delete-dialog.component';
import { WechatProductImageService } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.service';

describe('Component Tests', () => {

    describe('WechatProductImage Management Delete Component', () => {
        let comp: WechatProductImageDeleteDialogComponent;
        let fixture: ComponentFixture<WechatProductImageDeleteDialogComponent>;
        let service: WechatProductImageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductImageDeleteDialogComponent],
                providers: [
                    WechatProductImageService
                ]
            })
            .overrideTemplate(WechatProductImageDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductImageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductImageService);
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
