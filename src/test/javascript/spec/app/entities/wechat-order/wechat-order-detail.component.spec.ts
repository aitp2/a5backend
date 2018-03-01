/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order-detail.component';
import { WechatOrderService } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.service';
import { WechatOrder } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.model';

describe('Component Tests', () => {

    describe('WechatOrder Management Detail Component', () => {
        let comp: WechatOrderDetailComponent;
        let fixture: ComponentFixture<WechatOrderDetailComponent>;
        let service: WechatOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderDetailComponent],
                providers: [
                    WechatOrderService
                ]
            })
            .overrideTemplate(WechatOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatOrder(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
