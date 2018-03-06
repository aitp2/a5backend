/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { A5BackendTestModule } from '../../../test.module';
import { MessageDetailComponent } from '../../../../../../main/webapp/app/entities/message/message-detail.component';
import { MessageService } from '../../../../../../main/webapp/app/entities/message/message.service';
import { Message } from '../../../../../../main/webapp/app/entities/message/message.model';

describe('Component Tests', () => {

    describe('Message Management Detail Component', () => {
        let comp: MessageDetailComponent;
        let fixture: ComponentFixture<MessageDetailComponent>;
        let service: MessageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [A5BackendTestModule],
                declarations: [MessageDetailComponent],
                providers: [
                    MessageService
                ]
            })
            .overrideTemplate(MessageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Message(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.message).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
