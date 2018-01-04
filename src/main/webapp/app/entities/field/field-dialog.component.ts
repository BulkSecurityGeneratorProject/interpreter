import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Field } from './field.model';
import { FieldPopupService } from './field-popup.service';
import { FieldService } from './field.service';

@Component({
    selector: 'jhi-field-dialog',
    templateUrl: './field-dialog.component.html'
})
export class FieldDialogComponent implements OnInit {

    field: Field;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private fieldService: FieldService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.field, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.field.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fieldService.update(this.field));
        } else {
            this.subscribeToSaveResponse(
                this.fieldService.create(this.field));
        }
    }

    private subscribeToSaveResponse(result: Observable<Field>) {
        result.subscribe((res: Field) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Field) {
        this.eventManager.broadcast({ name: 'fieldListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-field-popup',
    template: ''
})
export class FieldPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldPopupService: FieldPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fieldPopupService
                    .open(FieldDialogComponent as Component, params['id']);
            } else {
                this.fieldPopupService
                    .open(FieldDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
