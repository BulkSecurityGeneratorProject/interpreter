/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { InterpreterTestModule } from '../../../test.module';
import { ConnectionDetailComponent } from '../../../../../../main/webapp/app/entities/connection/connection-detail.component';
import { ConnectionService } from '../../../../../../main/webapp/app/entities/connection/connection.service';
import { Connection } from '../../../../../../main/webapp/app/entities/connection/connection.model';

describe('Component Tests', () => {

    describe('Connection Management Detail Component', () => {
        let comp: ConnectionDetailComponent;
        let fixture: ComponentFixture<ConnectionDetailComponent>;
        let service: ConnectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InterpreterTestModule],
                declarations: [ConnectionDetailComponent],
                providers: [
                    ConnectionService
                ]
            })
            .overrideTemplate(ConnectionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConnectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConnectionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Connection(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.connection).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
