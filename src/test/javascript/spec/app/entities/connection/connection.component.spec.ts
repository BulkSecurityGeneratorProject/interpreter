/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { InterpreterTestModule } from '../../../test.module';
import { ConnectionComponent } from '../../../../../../main/webapp/app/entities/connection/connection.component';
import { ConnectionService } from '../../../../../../main/webapp/app/entities/connection/connection.service';
import { Connection } from '../../../../../../main/webapp/app/entities/connection/connection.model';

describe('Component Tests', () => {

    describe('Connection Management Component', () => {
        let comp: ConnectionComponent;
        let fixture: ComponentFixture<ConnectionComponent>;
        let service: ConnectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InterpreterTestModule],
                declarations: [ConnectionComponent],
                providers: [
                    ConnectionService
                ]
            })
            .overrideTemplate(ConnectionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConnectionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConnectionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Connection(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.connections[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
