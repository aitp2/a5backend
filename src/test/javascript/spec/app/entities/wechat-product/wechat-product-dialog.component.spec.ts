/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product-dialog.component';
import { WechatProductService } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.service';
import { WechatProduct } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.model';
import { WechatUserService } from '../../../../../../main/webapp/app/entities/wechat-user';
import { WechatWishListService } from '../../../../../../main/webapp/app/entities/wechat-wish-list';

describe('Component Tests', () => {

    describe('WechatProduct Management Dialog Component', () => {
        let comp: WechatProductDialogComponent;
        let fixture: ComponentFixture<WechatProductDialogComponent>;
        let service: WechatProductService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductDialogComponent],
                providers: [
                    WechatUserService,
                    WechatWishListService,
                    WechatProductService
                ]
            })
            .overrideTemplate(WechatProductDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatProduct(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.wechatProduct = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatProductListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatProduct();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.wechatProduct = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatProductListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
