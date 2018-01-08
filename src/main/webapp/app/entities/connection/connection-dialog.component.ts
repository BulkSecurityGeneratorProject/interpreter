import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Connection } from './connection.model';
import { ConnectionPopupService } from './connection-popup.service';
import { ConnectionService } from './connection.service';

@Component({
    selector: 'jhi-connection-dialog',
    templateUrl: './connection-dialog.component.html'
})
export class ConnectionDialogComponent implements OnInit {

    connection: Connection;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private connectionService: ConnectionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.connection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.connectionService.update(this.connection));
        } else {
            this.subscribeToSaveResponse(
                this.connectionService.create(this.connection));
        }
    }

    private subscribeToSaveResponse(result: Observable<Connection>) {
        result.subscribe((res: Connection) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Connection) {
        this.eventManager.broadcast({ name: 'connectionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-connection-popup',
    template: ''
})
export class ConnectionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private connectionPopupService: ConnectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.connectionPopupService
                    .open(ConnectionDialogComponent as Component, params['id']);
            } else {
                this.connectionPopupService
                    .open(ConnectionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
