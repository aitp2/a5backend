/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { WechatOrderItemComponent } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.component';
import { WechatOrderItemService } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.service';
import { WechatOrderItem } from '../../../../../../main/webapp/app/entities/wechat-order-item/wechat-order-item.model';

describe('Component Tests', () => {

    describe('WechatOrderItem Management Component', () => {
        let comp: WechatOrderItemComponent;
        let fixture: ComponentFixture<WechatOrderItemComponent>;
        let service: WechatOrderItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatOrderItemComponent],
                providers: [
                    WechatOrderItemService
                ]
            })
            .overrideTemplate(WechatOrderItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatOrderItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatOrderItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatOrderItem(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatOrderItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
