/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { CollectListDetailComponent } from '../../../../../../main/webapp/app/entities/collect-list/collect-list-detail.component';
import { CollectListService } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.service';
import { CollectList } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.model';

describe('Component Tests', () => {

    describe('CollectList Management Detail Component', () => {
        let comp: CollectListDetailComponent;
        let fixture: ComponentFixture<CollectListDetailComponent>;
        let service: CollectListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [CollectListDetailComponent],
                providers: [
                    CollectListService
                ]
            })
            .overrideTemplate(CollectListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollectListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollectListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CollectList(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.collectList).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
