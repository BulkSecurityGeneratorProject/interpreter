/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { InterpreterTestModule } from '../../../test.module';
import { ConnectionDialogComponent } from '../../../../../../main/webapp/app/entities/connection/connection-dialog.component';
import { ConnectionService } from '../../../../../../main/webapp/app/entities/connection/connection.service';
import { Connection } from '../../../../../../main/webapp/app/entities/connection/connection.model';

describe('Component Tests', () => {

    describe('Connection Management Dialog Component', () => {
        let comp: ConnectionDialogComponent;
        let fixture: ComponentFixture<ConnectionDialogComponent>;
        let service: ConnectionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InterpreterTestModule],
                declarations: [ConnectionDialogComponent],
                providers: [
                    ConnectionService
                ]
            })
            .overrideTemplate(ConnectionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConnectionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConnectionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Connection(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.connection = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'connectionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Connection();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.connection = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'connectionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
