/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { WechatWishListDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list-detail.component';
import { WechatWishListService } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.service';
import { WechatWishList } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.model';

describe('Component Tests', () => {

    describe('WechatWishList Management Detail Component', () => {
        let comp: WechatWishListDetailComponent;
        let fixture: ComponentFixture<WechatWishListDetailComponent>;
        let service: WechatWishListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatWishListDetailComponent],
                providers: [
                    WechatWishListService
                ]
            })
            .overrideTemplate(WechatWishListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatWishListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatWishListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatWishList(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatWishList).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
