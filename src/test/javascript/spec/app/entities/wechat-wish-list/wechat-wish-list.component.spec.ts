/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { WechatWishListComponent } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.component';
import { WechatWishListService } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.service';
import { WechatWishList } from '../../../../../../main/webapp/app/entities/wechat-wish-list/wechat-wish-list.model';

describe('Component Tests', () => {

    describe('WechatWishList Management Component', () => {
        let comp: WechatWishListComponent;
        let fixture: ComponentFixture<WechatWishListComponent>;
        let service: WechatWishListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatWishListComponent],
                providers: [
                    WechatWishListService
                ]
            })
            .overrideTemplate(WechatWishListComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatWishListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatWishListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatWishList(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatWishLists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
