/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { InterpreterTestModule } from '../../../test.module';
import { FieldComponent } from '../../../../../../main/webapp/app/entities/field/field.component';
import { FieldService } from '../../../../../../main/webapp/app/entities/field/field.service';
import { Field } from '../../../../../../main/webapp/app/entities/field/field.model';

describe('Component Tests', () => {

    describe('Field Management Component', () => {
        let comp: FieldComponent;
        let fixture: ComponentFixture<FieldComponent>;
        let service: FieldService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InterpreterTestModule],
                declarations: [FieldComponent],
                providers: [
                    FieldService
                ]
            })
            .overrideTemplate(FieldComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FieldComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FieldService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Field(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fields[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
