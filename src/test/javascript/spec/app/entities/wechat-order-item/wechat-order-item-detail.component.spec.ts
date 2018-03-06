/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderItemDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item-detail.component';
import { WechatOrderItemService } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.service';
import { WechatOrderItem } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.model';

describe('Component Tests', () => {

    describe('WechatOrderItem Management Detail Component', () => {
        let comp: WechatOrderItemDetailComponent;
        let fixture: ComponentFixture<WechatOrderItemDetailComponent>;
        let service: WechatOrderItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderItemDetailComponent],
                providers: [
                    WechatOrderItemService
                ]
            })
            .overrideTemplate(WechatOrderItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatOrderItem(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatOrderItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
