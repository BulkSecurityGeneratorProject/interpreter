import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Field } from './field.model';
import { FieldService } from './field.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-field',
    templateUrl: './field.component.html'
})
export class FieldComponent implements OnInit, OnDestroy {
fields: Field[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fieldService: FieldService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fieldService.query().subscribe(
            (res: ResponseWrapper) => {
                this.fields = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFields();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Field) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInFields() {
        this.eventSubscriber = this.eventManager.subscribe('fieldListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
