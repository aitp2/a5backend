/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { WechatProductImageComponent } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.component';
import { WechatProductImageService } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.service';
import { WechatProductImage } from '../../../../../../main/webapp/app/entities/wechat-product-image/wechat-product-image.model';

describe('Component Tests', () => {

    describe('WechatProductImage Management Component', () => {
        let comp: WechatProductImageComponent;
        let fixture: ComponentFixture<WechatProductImageComponent>;
        let service: WechatProductImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [WechatProductImageComponent],
                providers: [
                    WechatProductImageService
                ]
            })
            .overrideTemplate(WechatProductImageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatProductImageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatProductImageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatProductImage(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatProductImages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
