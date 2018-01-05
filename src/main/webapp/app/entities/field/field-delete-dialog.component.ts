import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Field } from './field.model';
import { FieldPopupService } from './field-popup.service';
import { FieldService } from './field.service';

@Component({
    selector: 'jhi-field-delete-dialog',
    templateUrl: './field-delete-dialog.component.html'
})
export class FieldDeleteDialogComponent {

    field: Field;

    constructor(
        private fieldService: FieldService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fieldService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fieldListModification',
                content: 'Deleted an field'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-field-delete-popup',
    template: ''
})
export class FieldDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldPopupService: FieldPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fieldPopupService
                .open(FieldDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
