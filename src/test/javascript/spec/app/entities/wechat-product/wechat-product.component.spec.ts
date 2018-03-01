/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductComponent } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.component';
import { WechatProductService } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.service';
import { WechatProduct } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.model';

describe('Component Tests', () => {

    describe('WechatProduct Management Component', () => {
        let comp: WechatProductComponent;
        let fixture: ComponentFixture<WechatProductComponent>;
        let service: WechatProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductComponent],
                providers: [
                    WechatProductService
                ]
            })
            .overrideTemplate(WechatProductComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatProduct(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatProducts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
