/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product-detail.component';
import { WechatProductService } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.service';
import { WechatProduct } from '../../../../../../main/webapp/app/entities/wechat-product/wechat-product.model';

describe('Component Tests', () => {

    describe('WechatProduct Management Detail Component', () => {
        let comp: WechatProductDetailComponent;
        let fixture: ComponentFixture<WechatProductDetailComponent>;
        let service: WechatProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductDetailComponent],
                providers: [
                    WechatProductService
                ]
            })
            .overrideTemplate(WechatProductDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatProduct(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatProduct).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
