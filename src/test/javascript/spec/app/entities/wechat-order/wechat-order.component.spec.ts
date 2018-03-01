/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderComponent } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.component';
import { WechatOrderService } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.service';
import { WechatOrder } from '../../../../../../main/webapp/app/entities/wechat-order/wechat-order.model';

describe('Component Tests', () => {

    describe('WechatOrder Management Component', () => {
        let comp: WechatOrderComponent;
        let fixture: ComponentFixture<WechatOrderComponent>;
        let service: WechatOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderComponent],
                providers: [
                    WechatOrderService
                ]
            })
            .overrideTemplate(WechatOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatOrder(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
