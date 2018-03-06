/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { A5BackendTestModule } from '../../../test.module';
import { MessageComponent } from '../../../../../../main/webapp/app/entities/message/message.component';
import { MessageService } from '../../../../../../main/webapp/app/entities/message/message.service';
import { Message } from '../../../../../../main/webapp/app/entities/message/message.model';

describe('Component Tests', () => {

    describe('Message Management Component', () => {
        let comp: MessageComponent;
        let fixture: ComponentFixture<MessageComponent>;
        let service: MessageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [MessageComponent],
                providers: [
                    MessageService
                ]
            })
            .overrideTemplate(MessageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Message(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.messages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
