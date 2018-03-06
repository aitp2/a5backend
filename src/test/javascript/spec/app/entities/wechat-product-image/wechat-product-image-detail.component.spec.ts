/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductImageDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image-detail.component';
import { WechatProductImageService } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.service';
import { WechatProductImage } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.model';

describe('Component Tests', () => {

    describe('WechatProductImage Management Detail Component', () => {
        let comp: WechatProductImageDetailComponent;
        let fixture: ComponentFixture<WechatProductImageDetailComponent>;
        let service: WechatProductImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductImageDetailComponent],
                providers: [
                    WechatProductImageService
                ]
            })
            .overrideTemplate(WechatProductImageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductImageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductImageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatProductImage(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatProductImage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
