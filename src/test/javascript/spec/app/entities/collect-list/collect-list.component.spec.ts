/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { CollectListComponent } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.component';
import { CollectListService } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.service';
import { CollectList } from '../../../../../../main/webapp/app/entities/collect-list/collect-list.model';

describe('Component Tests', () => {

    describe('CollectList Management Component', () => {
        let comp: CollectListComponent;
        let fixture: ComponentFixture<CollectListComponent>;
        let service: CollectListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [CollectListComponent],
                providers: [
                    CollectListService
                ]
            })
            .overrideTemplate(CollectListComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollectListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollectListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CollectList(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.collectLists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
