/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductImageDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image-dialog.component';
import { WechatProductImageService } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.service';
import { WechatProductImage } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.model';
import { WechatProductService } from '../../../../../../main/webapp/app/entities/wechat-product';

describe('Component Tests', () => {

    describe('WechatProductImage Management Dialog Component', () => {
        let comp: WechatProductImageDialogComponent;
        let fixture: ComponentFixture<WechatProductImageDialogComponent>;
        let service: WechatProductImageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductImageDialogComponent],
                providers: [
                    WechatProductService,
                    WechatProductImageService
                ]
            })
            .overrideTemplate(WechatProductImageDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductImageDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductImageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatProductImage(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.wechatProductImage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatProductImageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatProductImage();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.wechatProductImage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatProductImageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
