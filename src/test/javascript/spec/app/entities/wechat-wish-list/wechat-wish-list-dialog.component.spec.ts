/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { A5BackendTestModule } from '../../../test.module';
import { WechatWishListDialogComponent } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list-dialog.component';
import { WechatWishListService } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.service';
import { WechatWishList } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.model';
import { WechatUserService } from '../../../../../../main/webapp/app/entities/wechat-user';

describe('Component Tests', () => {

    describe('WechatWishList Management Dialog Component', () => {
        let comp: WechatWishListDialogComponent;
        let fixture: ComponentFixture<WechatWishListDialogComponent>;
        let service: WechatWishListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatWishListDialogComponent],
                providers: [
                    WechatUserService,
                    WechatWishListService
                ]
            })
            .overrideTemplate(WechatWishListDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatWishListDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatWishListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatWishList(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.wechatWishList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatWishListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WechatWishList();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.wechatWishList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'wechatWishListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
